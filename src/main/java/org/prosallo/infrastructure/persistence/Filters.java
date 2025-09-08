package org.prosallo.infrastructure.persistence;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.function.Function;

public final class Filters {

    private Filters() {
    }

    public static <T, V> Specification<T> eq(Function<Root<T>, Path<V>> selector, V value) {
        return (root, query, cb) -> cb.equal(selector.apply(root), value);
    }
}
