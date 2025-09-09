package org.prosallo.infrastructure.persistence;

public record Pageable(int pageNumber, int pageSize, Sort sort) {}
