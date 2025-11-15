package com.geopslabs.geops.backend.identity.domain.model.queries;

/**
 * GetUserByEmailQuery
 *
 * Query record to retrieve a specific user by their email address.
 * This query is essential for authentication and "get me" operations
 * where the user's identity is determined by their email
 *
 * @summary Query to retrieve a user by their email
 * @param email The email address of the user to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetUserByEmailQuery(String email) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if email is null or empty
     */
    public GetUserByEmailQuery {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
    }
}

