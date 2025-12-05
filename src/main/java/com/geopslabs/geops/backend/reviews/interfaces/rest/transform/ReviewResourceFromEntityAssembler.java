package com.geopslabs.geops.backend.reviews.interfaces.rest.transform;

import com.geopslabs.geops.backend.reviews.domain.model.aggregates.Review;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.ReviewResource;

/**
 * ReviewResourceFromEntityAssembler
 *
 * Assembler class responsible for converting Review entity objects
 * to ReviewResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer Resources for API responses.
 *
 * @summary Converts Review entity to ReviewResource
 * @since 1.0
 * @author GeOps Labs
 */
public class ReviewResourceFromEntityAssembler {

    /**
     * Converts a Review entity to a ReviewResource
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses
     * It extracts all relevant review information for client consumption
     *
     * @param entity The Review entity from the domain layer
     * @return A ReviewResource ready for REST API response
     */
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
            entity.getId(),
            entity.getOfferId(),
            entity.getUserId(),
            entity.getUserName(),
            entity.getRating(),
            entity.getText(),
            entity.getLikes(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }
}
