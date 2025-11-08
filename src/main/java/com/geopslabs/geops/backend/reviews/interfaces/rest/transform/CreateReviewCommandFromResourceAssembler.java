package com.geopslabs.geops.backend.reviews.interfaces.rest.transform;

import com.geopslabs.geops.backend.reviews.domain.model.commands.CreateReviewCommand;
import com.geopslabs.geops.backend.reviews.interfaces.rest.resources.CreateReviewResource;

/**
 * CreateReviewCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreateReviewResource objects
 * to CreateReviewCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands
 *
 * @summary Converts CreateReviewResource to CreateReviewCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateReviewCommandFromResourceAssembler {

    /**
     * Converts a CreateReviewResource to a CreateReviewCommand
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services
     * All validation is handled at the command level
     *
     * @param resource The CreateReviewResource from the REST API request
     * @return A CreateReviewCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource) {
        return new CreateReviewCommand(
            resource.offerId(),
            resource.userId(),
            resource.userName(),
            resource.rating(),
            resource.text()
        );
    }
}
