package com.geopslabs.geops.backend.favorites.interfaces.rest.transform;

import com.geopslabs.geops.backend.favorites.domain.model.commands.DeleteFavoriteCommand;
import com.geopslabs.geops.backend.favorites.interfaces.rest.resources.DeleteFavoriteResource;

/**
 * DeleteFavoriteCommandFromResourceAssembler
 *
 * Assembler class to transform DeleteFavoriteResource into DeleteFavoriteCommand
 * This class follows the Assembler pattern to separate REST resources from domain commands
 *
 * @summary Assembler for converting delete favorite resource to command
 * @since 1.0
 * @author GeOps Labs
 */
public class DeleteFavoriteCommandFromResourceAssembler {

    /**
     * Converts a DeleteFavoriteResource to a DeleteFavoriteCommand
     *
     * @param resource The delete favorite resource from REST request
     * @return A DeleteFavoriteCommand ready for processing by the service layer
     * @throws IllegalArgumentException if resource contains invalid data
     */
    public static DeleteFavoriteCommand toCommandFromResource(DeleteFavoriteResource resource) {
        return new DeleteFavoriteCommand(
            resource.userId(),
            resource.offerId()
        );
    }
}

