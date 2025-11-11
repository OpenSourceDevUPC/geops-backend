package com.geopslabs.geops.backend.reviews.interfaces.rest;

import com.geopslabs.geops.backend.reviews.domain.model.queries.GetAllReviewsQuery;
import com.geopslabs.geops.backend.reviews.domain.model.queries.GetReviewByIdQuery;
import com.geopslabs.geops.backend.reviews.domain.model.queries.GetReviewsByOfferIdQuery;
import com.geopslabs.geops.backend.reviews.domain.services.ReviewCommandService;
import com.geopslabs.geops.backend.reviews.domain.services.ReviewQueryService;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.CreateReviewResource;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.ReviewResource;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.UpdateReviewResource;
import com.geopslabs.geops.backend.reviews.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.geopslabs.geops.backend.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import com.geopslabs.geops.backend.reviews.interfaces.rest.transform.UpdateReviewCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * ReviewController
 *
 * REST controller for managing reviews for offers
 * This controller provides endpoints to create, retrieve, update, and delete reviews
 *
 * @summary REST controller for review management
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Reviews", description = "Review operations and management")
@RestController
@RequestMapping(value = "/api/v1/reviews", produces = APPLICATION_JSON_VALUE)
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param reviewCommandService Service for handling review commands
     * @param reviewQueryService Service for handling review queries
     */
    public ReviewController(ReviewCommandService reviewCommandService,
                           ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    /**
     * Creates a new review
     *
     * @param resource The review creation request data
     * @return ResponseEntity containing the created review or error status
     */
    @Operation(summary = "Create new review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ReviewResource> create(@RequestBody CreateReviewResource resource) {
        var command = CreateReviewCommandFromResourceAssembler.toCommandFromResource(resource);
        var review = reviewCommandService.handle(command);

        if (review.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return new ResponseEntity<>(reviewResource, CREATED);
    }

    /**
     * Retrieves a review by its unique identifier
     *
     * @param id The unique identifier of the review
     * @return ResponseEntity containing the review data or not found status
     */
    @Operation(summary = "Get review by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review found"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "400", description = "Invalid review ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResource> getById(
            @Parameter(description = "Review unique identifier") @PathVariable Long id) {
        var query = new GetReviewByIdQuery(id);
        var review = reviewQueryService.handle(query);

        if (review.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

    /**
     * Retrieves all reviews or reviews filtered by offer ID
     *
     * This endpoint support two modes:
     * 1. GET /reviews - retrieves all reviews
     * 2. GET /reviews?offerId=1 - retrieves reviews for a specific offer ID
     *
     * @param offerId Optional offer ID to filter reviews
     * @return ResponseEntity containing the list of reviews
     */
    @Operation(summary = "Get all reviews or reviews by offer ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReviewResource>> getReviews(
            @Parameter(description = "Optional offer ID to filter reviews")
            @RequestParam(required = false) String offerId) {

        List<ReviewResource> reviewResources;

        if (offerId != null && !offerId.isBlank()) {
            // GET /reviews?offerId=1
            var query = new GetReviewsByOfferIdQuery(offerId);
            var reviews = reviewQueryService.handle(query);
            reviewResources = reviews.stream()
                    .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            // GET /reviews
            var query = new GetAllReviewsQuery();
            var reviews = reviewQueryService.handle(query);
            reviewResources = reviews.stream()
                    .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }

        return ResponseEntity.ok(reviewResources);
    }

    /**
     * Updates an existing review by ID
     *
     * @param id The unique identifier of the review to update
     * @param resource The review update request data
     * @return ResponseEntity containing the updated review or error status
     */
    @Operation(summary = "Update review (PATCH)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review updated successfully"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ReviewResource> update(
            @Parameter(description = "Review unique identifier") @PathVariable Long id,
            @RequestBody UpdateReviewResource resource) {

        // First check if review exists
        var existingReviewQuery = new GetReviewByIdQuery(id);
        var existingReview = reviewQueryService.handle(existingReviewQuery);

        if (existingReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Create update command and handle it
        var updateCommand = UpdateReviewCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var review = reviewCommandService.handle(updateCommand);

        if (review.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

    /**
     * Deletes a review by ID
     *
     * @param id The unique identifier of the review to delete
     * @return ResponseEntity with no content or error status
     */
    @Operation(summary = "Delete review")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Review not found"),
        @ApiResponse(responseCode = "400", description = "Invalid review ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Review unique identifier") @PathVariable Long id) {

        boolean deleted = reviewCommandService.handleDelete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
