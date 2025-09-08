package org.prosallo.infrastructure.persistence;

/**
 * Interface for persistable entities.
 *
 * @param <T> the type of the entity's identifier
 */
public interface Persistable<T> {

    /**
     * Returns the identifier of the entity.
     *
     * @return the entity's ID.
     */
    T getId();

    /**
     * Checks if the entity is new.
     *
     * <p>An entity is considered new if has not been persisted to the database yer. This is
     * typically determined by checking if its ID is null
     *
     * @return {@code true} if the entity is new, {@code false} otherwise.
     */
    boolean isNew();
}
