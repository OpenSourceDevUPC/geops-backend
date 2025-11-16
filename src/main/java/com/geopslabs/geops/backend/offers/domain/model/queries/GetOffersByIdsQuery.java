package com.geopslabs.geops.backend.offers.domain.model.queries;

import java.util.List;

/**
 * GetOffersByIdsQuery
 *
 * Query record to retrieve multiple offers by their unique identifiers.
 * This query is used to fetch specific offer details when needed
 * for display, processing, or analytics
 *
 * @summary Query to retrieve offers by their IDs
 * @param ids The list of unique identifiers of the offers
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetOffersByIdsQuery(List<Long> ids) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetOffersByIdsQuery {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ids list cannot be null or empty");
        }

        if (ids.stream().anyMatch(id -> id == null || id <= 0)) {
            throw new IllegalArgumentException("all ids must be positive");
        }
    }
}
