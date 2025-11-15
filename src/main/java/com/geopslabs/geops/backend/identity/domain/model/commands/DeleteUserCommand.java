package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * DeleteUserCommand
 *
 * Command record for deleting a user from the system.
 * This command permanently removes a user and their associated data
 *
 * @summary Command to delete a user
 * @param id The unique identifier of the user to delete
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteUserCommand(Long id) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public DeleteUserCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

