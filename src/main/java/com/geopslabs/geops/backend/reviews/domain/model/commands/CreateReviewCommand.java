package com.geopslabs.geops.backend.reviews.domain.model.commands;

/**
 * CreateReviewCommand
 *
 * Command to create a new review for an offer
 * Contains all necessary data for review creation with validations
 *
 * @summary Command to create a new review
 * @param offerId  The ID of the offer being reviewed
 * @param userId   The ID of the user creating the review
 * @param userName The name of the user creating the review
 * @param rating   The rating given (1-5)
 * @param text     The review text/comment
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateReviewCommand(
        String offerId,
        String userId,
        String userName,
        Integer rating,
        String text
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateReviewCommand {
        if (offerId == null || offerId.isBlank()) {
            throw new IllegalArgumentException("offerId cannot be null or empty");
        }

        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("userName cannot be null or empty");
        }

        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("text cannot be null or empty");
        }

        if (text.length() > 2000) {
            throw new IllegalArgumentException("text cannot exceed 2000 characters");
        }
    }
}
