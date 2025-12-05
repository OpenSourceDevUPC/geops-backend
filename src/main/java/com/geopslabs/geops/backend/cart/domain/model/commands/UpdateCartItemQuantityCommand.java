package com.geopslabs.geops.backend.cart.domain.model.commands;

/**
 * UpdateCartItemQuantityCommand
 *
 * Command record that encapsulates the necessary data to update the quantity of an item in a cart.
 * This command validates input data to ensure the user ID, offer ID, and quantity are provided
 *
 * @summary Command to update cart item quantity
 * @param userId The unique identifier of the user
 * @param offerId The unique identifier of the offer
 * @param quantity The new quantity (0 or negative removes the item)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateCartItemQuantityCommand(
        Long userId,
        Long offerId,
        Integer quantity
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateCartItemQuantityCommand {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("quantity cannot be null");
        }
    }
}

