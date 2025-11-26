package com.geopslabs.geops.backend.identity.domain.model.queries;

/**
 * GetDetailsConsumerByUserIdQuery
 *
 * Query record for retrieving consumer details by user ID
 *
 * @summary Query to get consumer details for a specific user
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetDetailsConsumerByUserIdQuery(Long userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetDetailsConsumerByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

