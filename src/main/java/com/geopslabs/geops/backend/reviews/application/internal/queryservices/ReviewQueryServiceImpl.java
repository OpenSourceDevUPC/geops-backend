package com.geopslabs.geops.backend.reviews.application.internal.queryservices;

import com.geopslabs.geops.backend.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.backend.reviews.domain.model.queries.GetAllReviewsQuery;
import com.geopslabs.geops.backend.reviews.domain.model.queries.GetReviewByIdQuery;
import com.geopslabs.geops.backend.reviews.domain.model.queries.GetReviewsByOfferIdQuery;
import com.geopslabs.geops.backend.reviews.domain.services.ReviewQueryService;
import com.geopslabs.geops.backend.reviews.infrastructure.persistence.jpa.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ReviewQueryServiceImpl
 *
 * Implementation of the ReviewQueryService that handles all query operations
 * for reviews. This service implements the business logic for
 * retrieving and searching reviews following DDD principles
 *
 * @summary Implementation of review query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    /**
     * Constructor for dependency injection
     *
     * @param reviewRepository The repository for review data access
     */
    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        try {
            return reviewRepository.findAll();
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving all reviews: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        try {
            return reviewRepository.findById(query.id());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving review by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Review> handle(GetReviewsByOfferIdQuery query) {
        try {
            return reviewRepository.findByOfferIdOrderByCreatedAtDesc(query.offerId());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving reviews by offer ID: " + e.getMessage());
            return List.of();
        }
    }
}


