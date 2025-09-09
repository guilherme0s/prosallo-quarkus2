package org.prosallo.infrastructure.persistence;

import java.util.List;

@SuppressWarnings("unused")
public record Page<T>(List<T> content, long totalElements, int number, int size) {

    public int totalPages() {
        if (size <= 0) return 0;
        return (int) Math.ceil((double) totalElements / size);
    }

    public boolean isFirst() {
        return number <= 0;
    }

    public boolean isLast() {
        return number >= totalPages() - 1;
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }
}
