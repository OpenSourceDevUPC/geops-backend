package com.geopslabs.geops.backend.cart.domain.model.commands;

/**
 * ClearCartCommand
 *
 * Command record that encapsulates the necessary data to clear all items from a user's cart.
 * This command validates input data to ensure the user ID is provided
 *
 * @summary Command to clear all items from a cart
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record ClearCartCommand(String userId) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public ClearCartCommand {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}

