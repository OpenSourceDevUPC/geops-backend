package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.commands.CreateUserCommand;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.CreateUserResource;

/**
 * CreateUserCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreateUserResource objects
 * to CreateUserCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands
 *
 * @summary Converts CreateUserResource to CreateUserCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateUserCommandFromResourceAssembler {

    /**
     * Converts a CreateUserResource to a CreateUserCommand
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the command service
     *
     * @param resource The CreateUserResource from the REST API request
     * @return A CreateUserCommand ready for domain processing
     */
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return new CreateUserCommand(
            resource.name(),
            resource.email(),
            resource.password(),
            resource.role(),
            resource.plan()
        );
    }
}


