package org.prosallo.infrastructure.persistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public record Sort(List<Order> orders) {

    public static Sort by(Direction direction, String... properties) {
        List<Order> orders = Arrays.stream(properties)
                .map(prop -> new Order(direction, prop))
                .toList();
        return new Sort(orders);
    }

    public boolean isSorted() {
        return !orders.isEmpty();
    }

    public record Order(Direction direction, String property) implements Serializable {}
}
