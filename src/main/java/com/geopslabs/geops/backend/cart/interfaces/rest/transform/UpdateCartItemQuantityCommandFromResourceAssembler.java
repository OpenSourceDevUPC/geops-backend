package com.geopslabs.geops.backend.cart.interfaces.rest.transform;

import com.geopslabs.geops.backend.cart.domain.model.commands.UpdateCartItemQuantityCommand;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.UpdateCartItemQuantityResource;

/**
 * UpdateCartItemQuantityCommandFromResourceAssembler
 *
 * Assembler class to transform UpdateCartItemQuantityResource into UpdateCartItemQuantityCommand.
 * This class follows the DDD pattern for transforming REST resources into domain commands
 *
 * @summary Transforms update quantity resource to command
 * @since 1.0
 * @author GeOps Labs
 */
public class UpdateCartItemQuantityCommandFromResourceAssembler {

    /**
     * Transforms an UpdateCartItemQuantityResource into an UpdateCartItemQuantityCommand
     *
     * @param resource The update quantity resource
     * @param userId The user ID
     * @param offerId The offer ID
     * @return The UpdateCartItemQuantityCommand
     */
    public static UpdateCartItemQuantityCommand toCommandFromResource(
            UpdateCartItemQuantityResource resource,
            Long userId,
            Long offerId
    ) {
        return new UpdateCartItemQuantityCommand(
                userId,
                offerId,
                resource.quantity()
        );
    }
}
