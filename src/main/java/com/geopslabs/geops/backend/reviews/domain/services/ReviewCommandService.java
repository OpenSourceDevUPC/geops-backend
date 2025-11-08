package com.geopslabs.geops.backend.reviews.domain.services;

import com.geopslabs.geops.backend.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.backend.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.backend.reviews.domain.model.commands.UpdateReviewCommand;

import java.util.Optional;

/**
 * ReviewCommandService
 *
 * Domain service interface that defines command operations for reviews
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern
 *
 * @author GeOps Labs
 * @summary Service interface for handling review command operations
 * @since 1.0
 */
public interface ReviewCommandService {

    /**
     * Handles the creation of a new review
     *
     * This method processes the command to create a new review,
     * validates the input, and persists the review data
     *
     * @param command The command containing all necessary data for review creation
     * @return An Optional containing the created Review if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Review> handle(CreateReviewCommand command);

    /**
     * Handles the update of an existing review
     *
     * This method processes the command to update review data such as rating,
     * text comment, and likes. It performs partial updates based on provided fields
     *
     * @param command The command containing the review ID and updated data
     * @return An Optional containing the updated Review if successful, empty if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Review> handle(UpdateReviewCommand command);

    /**
     * Handles the deletion of a review by its unique identifier
     *
     * This method processes the deletion of a review from the system
     *
     * @param id The unique identifier of the review to delete
     * @return true if the review was successfully deleted, false if not found
     * @throws IllegalArgumentException if the ID is invalid
     */
    boolean handleDelete(Long id);
}
