package com.geopslabs.geops.backend.cart.interfaces.rest.transform;

import com.geopslabs.geops.backend.cart.domain.model.commands.AddItemToCartCommand;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartItemResource;

/**
 * AddItemToCartCommandFromResourceAssembler
 *
 * Assembler class to transform CartItemResource into AddItemToCartCommand.
 * This class follows the DDD pattern for transforming REST resources into domain commands
 *
 * @summary Transforms cart item resource to add item command
 * @since 1.0
 * @author GeOps Labs
 */
public class AddItemToCartCommandFromResourceAssembler {

    /**
     * Transforms a CartItemResource into an AddItemToCartCommand
     *
     * @param resource The cart item resource
     * @param userId The user ID
     * @return The AddItemToCartCommand
     */
    public static AddItemToCartCommand toCommandFromResource(CartItemResource resource, String userId) {
        return new AddItemToCartCommand(
                userId,
                resource.offerId(),
                resource.offerTitle(),
                resource.offerPrice(),
                resource.offerImageUrl(),
                resource.quantity()
        );
    }
}
