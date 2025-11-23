package com.geopslabs.geops.backend.cart.domain.model.queries;

/**
 * GetCartByUserIdQuery
 *
 * Query record to retrieve a specific cart by the user's unique identifier.
 * This query is the primary way to fetch a user's active shopping cart
 *
 * @summary Query to retrieve a cart by user ID
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetCartByUserIdQuery(String userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetCartByUserIdQuery {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}

