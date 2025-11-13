package com.geopslabs.geops.backend.reviews.interfaces.rest.transform;

import com.geopslabs.geops.backend.reviews.domain.model.commands.UpdateReviewCommand;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.UpdateReviewResource;

/**
 * UpdateReviewCommandFromResourceAssembler
 *
 * Assembler class responsible for converting UpdateReviewResource objects
 * to UpdateReviewCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands.
 *
 * @summary Converts UpdateReviewResource to UpdateReviewCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class UpdateReviewCommandFromResourceAssembler {

    /**
     * Converts a UpdateReviewResource to a UpdateReviewCommand
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services
     * All validation is handled at the command level
     *
     * @param id The ID of the review to update
     * @param resource The UpdateReviewResource from the REST API request
     * @return A UpdateReviewCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static UpdateReviewCommand toCommandFromResource(Long id, UpdateReviewResource resource) {
        return new UpdateReviewCommand(
            id,
            resource.text(),
            resource.likes()
        );
    }
}
