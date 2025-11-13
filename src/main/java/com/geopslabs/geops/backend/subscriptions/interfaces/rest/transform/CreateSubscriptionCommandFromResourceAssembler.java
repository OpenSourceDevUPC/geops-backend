package com.geopslabs.geops.backend.subscriptions.interfaces.rest.transform;

import com.geopslabs.geops.backend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;

/**
 * CreateSubscriptionCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreateSubscriptionResource objects
 * to CreateSubscriptionCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands.
 *
 * @summary Converts CreateSubscriptionResource to CreateSubscriptionCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateSubscriptionCommandFromResourceAssembler {

    /**
     * Converts a CreateSubscriptionResource to a CreateSubscriptionCommand.
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services.
     * All validation is handled at the command level.
     *
     * @param resource The CreateSubscriptionResource from the REST API request
     * @return A CreateSubscriptionCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(
            resource.price(),
            resource.recommended(),
            resource.type()
        );
    }
}
