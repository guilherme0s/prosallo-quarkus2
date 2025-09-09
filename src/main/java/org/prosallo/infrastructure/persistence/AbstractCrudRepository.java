package org.prosallo.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.sqm.PathElementException;
import org.jspecify.annotations.NonNull;
import org.prosallo.infrastructure.exception.InvalidSortPropertyException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCrudRepository<T extends Persistable<ID>, ID> implements CrudRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    /**
     * Constructs a new BaseRepository.
     *
     * <p>This constructor uses reflection to determine the entity class {@code T} from the generic
     * type arguments of the extending class.
     */
    @SuppressWarnings("unchecked")
    protected AbstractCrudRepository() {
        this.entityClass = (Class<T>) getGenericTypeArgument(getClass(), 0);
    }

    @Override
    public T save(@NonNull T entity) {
        if (entity.isNew()) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    @Override
    public Optional<T> findById(@NonNull ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public void delete(@NonNull T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @SafeVarargs
    protected final Optional<T> findOneBy(@NonNull Specification<T>... specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        Predicate[] predicates = toPredicates(cb, cq, root, specs);
        cq.select(root).where(predicates);

        return entityManager.createQuery(cq)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }

    @SafeVarargs
    protected final long countBy(@NonNull Specification<T>... specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);

        Predicate[] predicates = toPredicates(cb, cq, root, specs);
        cq.select(cb.count(root));
        if (predicates.length > 0) {
            cq.where(predicates);
        }

        return entityManager.createQuery(cq).getSingleResult();
    }

    /**
     * Retrieves all entities that match the given specifications.
     *
     * @param specs a varargs array of {@link Specification} criteria to apply to the query.
     * @return a {@link List} of matching entities; will never be {@code null}.
     */
    @SafeVarargs
    protected final List<T> findBy(@NonNull Specification<T>... specs) {
        return createQuery(specs).getResultList();
    }

    @SafeVarargs
    protected final Page<T> findAllBy(@NonNull Pageable pageable, @NonNull Specification<T>... specs) {
        long total = countBy(specs);

        if (total == 0) {
            return new Page<>(Collections.emptyList(), 0L, pageable.pageNumber(), pageable.pageSize());
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        Predicate[] predicates = toPredicates(cb, cq, root, specs);
        cq.select(root);
        if (predicates.length > 0) {
            cq.where(predicates);
        }

        Sort sort = pageable.sort();
        if (sort != null && sort.isSorted()) {
            jakarta.persistence.criteria.Order[] criteriaOrders = sort.orders().stream()
                    .map(o -> toCriteriaOrder(root, cb, o))
                    .toArray(jakarta.persistence.criteria.Order[]::new);
            cq.orderBy(criteriaOrders);
        }

        var query = entityManager.createQuery(cq);
        int pageSize = pageable.pageSize();
        int pageNumber = pageable.pageNumber();
        int firstResult = Math.multiplyExact(pageNumber, pageSize);

        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);

        List<T> content = query.getResultList();
        return new Page<>(content, total, pageNumber, pageSize);
    }

    @SafeVarargs
    private Predicate[] toPredicates(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root, Specification<T>... specs) {
        if (specs == null || specs.length == 0) return new Predicate[0];
        return Arrays.stream(specs)
                .filter(Objects::nonNull)
                .map(s -> s.toPredicate(root, cq, cb))
                .toArray(Predicate[]::new);
    }

    @SafeVarargs
    private TypedQuery<T> createQuery(@NonNull Specification<T>... specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);

        Predicate[] predicates = toPredicates(cb, cq, root, specs);
        if (predicates.length > 0) {
            cq.where(predicates);
        }

        return entityManager.createQuery(cq);
    }

    private jakarta.persistence.criteria.Order toCriteriaOrder(Root<T> root, CriteriaBuilder cb, Sort.Order order) {
        var expr = navigatePath(root, order.property());
        return order.direction() == Direction.ASC ? cb.asc(expr) : cb.desc(expr);
    }

    private Path<?> navigatePath(Root<T> root, @NonNull String propertyPath) {
        if (propertyPath.isBlank()) {
            throw new IllegalArgumentException("Property path cannot be null or blank");
        }

        try {
            String[] parts = propertyPath.split("\\.");
            Path<?> expr = root.get(parts[0].trim());
            for (int i = 1; i < parts.length; i++) {
                expr = expr.get(parts[i].trim());
            }
            return expr;
        } catch (PathElementException e) {
            throw new InvalidSortPropertyException(propertyPath);
        }
    }

    private Type getGenericTypeArgument(Class<?> clazz, int index) {
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType parameterized) {
            return parameterized.getActualTypeArguments()[index];
        }

        if (clazz.getSuperclass() != null) {
            return getGenericTypeArgument(clazz.getSuperclass(), index);
        }

        throw new IllegalArgumentException("Could not find generic type argument");
    }
}
