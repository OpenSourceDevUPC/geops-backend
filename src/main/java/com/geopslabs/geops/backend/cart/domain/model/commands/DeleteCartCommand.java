package com.geopslabs.geops.backend.cart.domain.model.commands;

/**
 * DeleteCartCommand
 *
 * Command record that encapsulates the necessary data to delete a cart.
 * This command validates input data to ensure the cart ID is provided
 *
 * @summary Command to delete a cart by ID
 * @param cartId The unique identifier of the cart
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteCartCommand(Long cartId) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public DeleteCartCommand {
        if (cartId == null || cartId <= 0) {
            throw new IllegalArgumentException("cartId must be positive");
        }
    }
}

