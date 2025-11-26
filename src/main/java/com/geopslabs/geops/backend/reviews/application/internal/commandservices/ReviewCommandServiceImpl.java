package com.geopslabs.geops.backend.reviews.application.internal.commandservices;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.offers.infrastructure.persistence.jpa.OfferRepository;
import com.geopslabs.geops.backend.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.backend.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.backend.reviews.domain.model.commands.UpdateReviewCommand;
import com.geopslabs.geops.backend.reviews.domain.services.ReviewCommandService;
import com.geopslabs.geops.backend.reviews.infrastructure.persistence.jpa.ReviewRepository;
import com.geopslabs.geops.backend.notifications.application.internal.outboundservices.NotificationFactoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * ReviewCommandServiceImpl
 *
 * Implementation of the ReviewCommandService that handles all command operations
 * for reviews. This service implements the business logic for
 * creating, updating, and managing reviews following DDD principles
 *
 * @author GeOps Labs
 * @summary Implementation of review command service operations
 * @since 1.0
 */
@Service
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final NotificationFactoryService notificationFactory;

    /**
     * Constructor for dependency injection
     *
     * @param reviewRepository The repository for review data access
     * @param userRepository The repository for user data access
     * @param offerRepository The repository for offer data access
     * @param notificationFactory Service to create notifications
     */
    public ReviewCommandServiceImpl(
        ReviewRepository reviewRepository,
        UserRepository userRepository,
        OfferRepository offerRepository,
        NotificationFactoryService notificationFactory
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.notificationFactory = notificationFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        try {
            // Load User and Offer entities
            var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.userId()));
            
            var offer = offerRepository.findById(command.offerId())
                .orElseThrow(() -> new IllegalArgumentException("Offer not found with id: " + command.offerId()));

            // Create review with entities
            var review = new Review(command, user, offer);

            // Save the review to the repository
            var savedReview = reviewRepository.save(review);

            // Create notification for review comment
            // For now, notify the user who created the review as confirmation
            notificationFactory.createReviewCommentNotification(
                command.userId(),
                command.offerId(),
                offer.getTitle(),
                "Tú"
            );

            return Optional.of(savedReview);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error creating review: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(UpdateReviewCommand command) {
        try {
            // Find the existing review by ID
            var existingReviewOpt = reviewRepository.findById(command.id());

            if (existingReviewOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingReview = existingReviewOpt.get();

            // Update the review with new data
            existingReview.updateReview(command);

            // Save the updated review (this should trigger @PreUpdate)
            var updatedReview = reviewRepository.save(existingReview);

            return Optional.of(updatedReview);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error updating review: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or negative");
        }

        try {
            // First check if review exists
            if (!reviewRepository.existsById(id)) {
                return false;
            }

            // Delete the review
            reviewRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error deleting review: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
