package com.geopslabs.geops.backend.favorites.interfaces.rest.transform;

import com.geopslabs.geops.backend.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.backend.favorites.interfaces.rest.resources.CreateFavoriteResource;

/**
 * CreateFavoriteCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreateFavoriteResource objects
 * to CreateFavoriteCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands
 *
 * @summary Converts CreateFavoriteResource to CreateFavoriteCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateFavoriteCommandFromResourceAssembler {

    /**
     * Converts a CreateFavoriteResource to a CreateFavoriteCommand
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services
     * All validation is handled at the command level
     *
     * @param resource The CreateFavoriteResource from the REST API request
     * @return A CreateFavoriteCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreateFavoriteCommand toCommandFromResource(CreateFavoriteResource resource) {
        return new CreateFavoriteCommand(
            resource.userId(),
            resource.offerId()
        );
    }
}

