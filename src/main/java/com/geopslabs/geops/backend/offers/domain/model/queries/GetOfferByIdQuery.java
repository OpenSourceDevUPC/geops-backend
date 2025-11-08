package com.geopslabs.geops.backend.offers.domain.model.queries;

/**
 * GetOfferByIdQuery
 *
 * Query record to retrieve a specific offer by its unique identifier.
 * This query is essential for fetching detailed information about a particular offer,
 * enabling users to view offer details, pricing, and availability
 *
 * @summary Query to retrieve an offer by its ID
 * @param id The unique identifier of the offer to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetOfferByIdQuery(Long id) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetOfferByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
}

