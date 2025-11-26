package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * UpdateUserCommand
 *
 * Command record for updating an existing user's information.
 * This command allows partial updates - null fields will not be updated
 *
 * @summary Command to update user information
 * @param id The unique identifier of the user to update
 * @param name The updated name (optional)
 * @param email The updated email (optional)
 * @param phone The updated phone (optional)
 * @param role The updated role (optional)
 * @param plan The updated subscription plan (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateUserCommand(
    Long id,
    String name,
    String email,
    String phone,
    String role,
    String plan
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateUserCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

