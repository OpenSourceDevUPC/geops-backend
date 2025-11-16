package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateUserCommand;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UpdateUserResource;

/**
 * UpdateUserCommandFromResourceAssembler
 *
 * Assembler class responsible for converting UpdateUserResource objects
 * to UpdateUserCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands
 *
 * @summary Converts UpdateUserResource to UpdateUserCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class UpdateUserCommandFromResourceAssembler {

    /**
     * Converts an UpdateUserResource to an UpdateUserCommand
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the command service
     *
     * @param id The unique identifier of the user to update
     * @param resource The UpdateUserResource from the REST API request
     * @return An UpdateUserCommand ready for domain processing
     */
    public static UpdateUserCommand toCommandFromResource(Long id, UpdateUserResource resource) {
        return new UpdateUserCommand(
            id,
            resource.name(),
            resource.email(),
            resource.role(),
            resource.plan()
        );
    }
}

