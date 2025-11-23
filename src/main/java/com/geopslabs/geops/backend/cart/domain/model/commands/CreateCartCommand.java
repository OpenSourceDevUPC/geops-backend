package com.geopslabs.geops.backend.cart.domain.model.commands;

/**
 * CreateCartCommand
 *
 * Command record that encapsulates the necessary data to create a new shopping cart.
 * This command validates input data to ensure the user ID is provided
 *
 * @summary Command to create a new cart for a user
 * @param userId The unique identifier of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateCartCommand(String userId) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateCartCommand {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}
