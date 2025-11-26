package com.geopslabs.geops.backend.identity.domain.model.queries;

/**
 * GetDetailsOwnerByUserIdQuery
 *
 * Query record for retrieving owner details by user ID
 *
 * @summary Query to get owner details for a specific user
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetDetailsOwnerByUserIdQuery(Long userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetDetailsOwnerByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

