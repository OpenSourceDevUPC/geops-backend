package com.geopslabs.geops.backend.favorites.domain.model.queries;

/**
 * GetFavoritesByUserIdQuery
 *
 * Query record to retrieve all favorite items associated with a specific user
 * This query is used to fetch user favorite items for display or processing
 *
 * @summary Query to retrieve favorites by user ID
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetFavoritesByUserIdQuery(Long userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetFavoritesByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}
