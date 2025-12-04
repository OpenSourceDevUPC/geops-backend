package com.geopslabs.geops.backend.offers.interfaces.rest.transform;

import com.geopslabs.geops.backend.offers.domain.model.commands.CreateOfferCommand;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.CreateOfferResource;

/**
 * CreateOfferCommandFromResourceAssembler
 * Assembler class responsible for converting CreateOfferResource objects
 * to CreateOfferCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands
 *
 * @summary Converts CreateOfferResource to CreateOfferCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateOfferCommandFromResourceAssembler {

    /**
     * Converts a CreateOfferResource to a CreateOfferCommand
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services
     * All validation is handled at the command level
     *
     * @param resource The CreateOfferResource from the REST API request
     * @return A CreateOfferCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreateOfferCommand toCommandFromResource(CreateOfferResource resource) {
        return new CreateOfferCommand(
            resource.campaignId(),
            resource.title(),
            resource.partner(),
            resource.price(),
            resource.codePrefix(),
            resource.validTo(),
            resource.rating(),
            resource.location(),
            resource.category(),
            resource.imageUrl()
        );
    }
}
