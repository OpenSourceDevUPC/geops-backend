package com.geopslabs.geops.backend.reviews.interfaces.rest.resources;

/**
 * ReviewResource
 *
 * Resource DTO for representing review data via REST API
 * This resource encapsulates the review information returned in API responses
 *
 * @summary Resource for review representation
 * @param id The unique identifier of the review
 * @param offerId The ID of the offer being reviewed
 * @param userId The ID of the user who created the review
 * @param userName The name of the user who created the review
 * @param rating The rating given in the review
 * @param text The text content of the review
 * @param likes The number of likes the review has received
 * @param createdAt The timestamp when the review was created
 * @param updatedAt The timestamp when the review was last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record ReviewResource(
    Long id,
    String offerId,
    String userId,
    String userName,
    Integer rating,
    String text,
    Integer likes,
    String createdAt,
    String updatedAt
) {

}
