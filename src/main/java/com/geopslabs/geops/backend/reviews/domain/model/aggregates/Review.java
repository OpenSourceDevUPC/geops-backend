package com.geopslabs.geops.backend.reviews.domain.model.aggregates;

import com.geopslabs.geops.backend.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.backend.reviews.domain.model.commands.UpdateReviewCommand;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Review Aggregate Root
 *
 * This aggregate represents a review/comment for an offer in the GeOps platform
 * It manages user feedback including ratings, text comments, and likes
 * Reviews are associated with offers and users through their identifiers
 *
 * @summary Manages user reviews and ratings for offers
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "reviews")
@Getter
public class Review extends AuditableAbstractAggregateRoot<Review> {

    /**
     * Offer identifier that this review belongs to
     */
    @Column(name = "offer_id", nullable = false)
    private String offerId;

    /**
     * User identifier who created the review
     */
    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * Name of the user who created the review
     */
    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    /**
     * Rating given to the offer (1-5 scale)
     */
    @Column(name = "rating", nullable = false)
    private Integer rating;

    /**
     * Review text content (maximum 2000 characters)
     */
    @Column(name = "text", nullable = false, length = 2000)
    private String text;

    /**
     * Number of likes this review has received
     */
    @Column(name = "likes", nullable = false)
    private Integer likes;

    /**
     * Default constructor for JPA
     */
    protected Review() {}

    /**
     * Creates a new Review from a CreateReviewCommand
     *
     * @param command The command containing review creation data
     */
    public Review(CreateReviewCommand command) {
        this.offerId = command.offerId();
        this.userId = command.userId();
        this.userName = command.userName();
        this.rating = command.rating();
        this.text = command.text();
        this.likes = 0; // Always starts with 0 likes
    }

    /**
     * Updates the review with new information
     * Only allows updating text and likes
     *
     * @param command The command containing updated review data
     */
    public void updateReview(UpdateReviewCommand command) {
        if (command.text() != null) {
            this.text = command.text();
        }
        if (command.likes() != null) {
            this.likes = command.likes();
        }
    }

    /**
     * Increments the likes count by 1
     * Business logic for when a user likes a review
     */
    public void incrementLikes() {
        this.likes++;
    }

    /**
     * Decrements the likes count by 1 (minimum 0)
     * Business logic for when a user unlikes a review
     */
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    /**
     * Validates if the rating is within valid range (1-5)
     *
     * @return true if rating is valid, false otherwise
     */
    public boolean hasValidRating() {
        return this.rating != null && this.rating >= 1 && this.rating <= 5;
    }

}
