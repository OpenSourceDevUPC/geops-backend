package com.geopslabs.geops.backend.favorites.domain.model.queries;

/**
 * GetFavoriteByUserIdAndOfferIdQuery
 *
 * Query record to retrieve a favorite by user ID and offer ID
 * This query is useful for checking if a specific offer is marked as favorite by a user
 *
 * @summary Query to retrieve a favorite by user ID and offer ID
 * @param userId  The unique identifier of the user
 * @param offerId The unique identifier of the offer
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetFavoriteByUserIdAndOfferIdQuery(Long userId, Long offerId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetFavoriteByUserIdAndOfferIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }
    }
}

