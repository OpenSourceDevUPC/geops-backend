package com.geopslabs.geops.backend.cart.domain.model.queries;

/**
 * GetCartByIdQuery
 *
 * Query record to retrieve a specific cart by its unique identifier.
 * This query is essential for fetching detailed information about a particular cart
 *
 * @summary Query to retrieve a cart by its ID
 * @param cartId The unique identifier of the cart to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetCartByIdQuery(Long cartId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetCartByIdQuery {
        if (cartId == null || cartId <= 0) {
            throw new IllegalArgumentException("cartId must be positive");
        }
    }
}

