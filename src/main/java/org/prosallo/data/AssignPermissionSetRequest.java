package org.prosallo.data;

import jakarta.validation.constraints.NotNull;

public record AssignPermissionSetRequest(@NotNull Long permissionSetId) {}
