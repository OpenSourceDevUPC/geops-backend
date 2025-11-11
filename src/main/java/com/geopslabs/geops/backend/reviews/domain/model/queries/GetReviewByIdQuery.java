package com.geopslabs.geops.backend.reviews.domain.model.queries;

/**
 * GetReviewByIdQuery
 *
 * Query record to retrieve a specific review by its unique identifier
 * This query is essential for fetching detailed information about a particular review
 * enabling functionalities such as displaying review details, editing, or deleting reviews
 *
 * @summary Query to retrieve a review by its unique identifier
 * @param id The unique identifier of the review to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetReviewByIdQuery(Long id) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetReviewByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
}

