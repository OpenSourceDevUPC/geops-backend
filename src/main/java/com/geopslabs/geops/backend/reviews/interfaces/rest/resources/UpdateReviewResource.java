package com.geopslabs.geops.backend.reviews.interfaces.rest.resources;

/**
 * UpdateReviewResource
 *
 * Resource representing the data required to update an existing review
 *
 * @summary Resource for updating a review
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateReviewResource(
    String text,
    Integer likes
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateReviewResource {
        if (text != null && text.isBlank()) {
            throw new IllegalArgumentException("text cannot be blank if provided");
        }

        if (text != null && text.length() > 2000) {
            throw new IllegalArgumentException("text cannot exceed 2000 characters");
        }

        if (likes != null && likes < 0) {
            throw new IllegalArgumentException("likes cannot be negative");
        }
    }
}
