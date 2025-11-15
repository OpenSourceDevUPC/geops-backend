package com.geopslabs.geops.backend.identity.domain.model.queries;

/**
 * GetUserByIdQuery
 *
 * Query record to retrieve a specific user by their unique identifier.
 * This query enables fetching detailed user information including
 * profile data, role, and subscription plan
 *
 * @summary Query to retrieve a user by their ID
 * @param id The unique identifier of the user to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetUserByIdQuery(Long id) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if id is null or negative
     */
    public GetUserByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

