package com.geopslabs.geops.backend.reviews.domain.model.commands;

/**
 * UpdateReviewCommand
 *
 * Command record for updating an existing review.
 * This command allows updating the review text and likes count
 *
 * @summary Command to update an existing review
 * @param id The unique identifier of the review to update
 * @param text The updated text of the review (optional)
 * @param likes The updated number of likes (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateReviewCommand(
        Long id,
        String text,
        Integer likes
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateReviewCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }

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


