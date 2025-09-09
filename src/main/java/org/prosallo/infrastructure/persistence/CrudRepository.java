package org.prosallo.infrastructure.persistence;

import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * A generic repository interface defining common data access operations.
 *
 * @param <T> the entity type, which must extend {@link Persistable}.
 * @param <ID> the type of the entity's identifier.
 */
public interface CrudRepository<T extends Persistable<ID>, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save
     * operation might have changed the entity instance completely.
     *
     * @param entity the entity to save; must not be {@code null}.
     * @return the saved entity; will never be {@code null}.
     */
    T save(@NonNull T entity);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve; must not be {@code null}.
     * @return an {@link Optional} containing the found entity or {@link Optional#empty()} if not
     * found.
     */
    Optional<T> findById(@NonNull ID id);

    /**
     * Deletes a given entity from the database.
     *
     * @param entity the entity to delete; must not be {@code null}.
     */
    void delete(@NonNull T entity);
}
