package com.geopslabs.geops.backend.reviews.domain.model.queries;

/**
 * GetReviewsByOfferIdQuery
 *
 * Query record to retrieve all reviews associated with a specific offer.
 * This query is used to fetch user feedback for an offer, which can be
 * displayed on the offer details page or used for analytics
 *
 * @summary Query to retrieve reviews by offer ID
 * @param offerId The unique identifier of the offer
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetReviewsByOfferIdQuery(String offerId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetReviewsByOfferIdQuery {
        if (offerId == null || offerId.isBlank()) {
            throw new IllegalArgumentException("offerId cannot be null or empty");
        }
    }
}

