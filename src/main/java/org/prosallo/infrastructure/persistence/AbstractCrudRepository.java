package org.prosallo.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

/**
 * An abstract base class for repositories providing common CRUD operations.
 *
 * @param <T> the entity type, which must extend {@link Persistable}.
 * @param <ID> the type of the entity's identifier.
 */
public abstract class AbstractCrudRepository<T extends Persistable<ID>, ID> {

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

    private Type getGenericTypeArgument(Class<?> clazz, int index) {
        Type genericSuperclass = clazz.getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[index];
        }

        if (clazz.getSuperclass() != null) {
            return getGenericTypeArgument(clazz.getSuperclass(), index);
        }

        throw new IllegalArgumentException("Could not find generic type argument");
    }

    public T save(@NonNull T entity) {
        if (entity.isNew()) {
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
    }

    public Optional<T> findById(@NonNull ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Deletes a given entity from the database.
     *
     * @param entity the entity to delete; must not be {@code null}.
     */
    public void delete(@NonNull T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @SafeVarargs
    protected final Optional<T> findOneBy(@NonNull Specification<T>... specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);

        Predicate[] predicates = Arrays.stream(specs)
                .map(s -> s.toPredicate(root, cq, cb))
                .toArray(Predicate[]::new);

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

        Predicate[] predicates = (specs.length == 0)
                ? new Predicate[0]
                : Arrays.stream(specs).map(s -> s.toPredicate(root, cq, cb)).toArray(Predicate[]::new);

        cq.select(cb.count(root));
        if (predicates.length > 0) {
            cq.where(predicates);
        }

        return entityManager.createQuery(cq).getSingleResult();
    }
}
