package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * CreateUserCommand
 *
 * Command record for creating a new user in the system.
 * This command encapsulates all necessary data to register a new user
 * including credentials, role, and subscription plan
 *
 * @summary Command to create a new user
 * @param name The full name of the user
 * @param email The email address of the user (unique identifier)
 * @param password The encrypted password for authentication
 * @param phone The phone number of the user (optional)
 * @param role The role of the user in the system
 * @param plan The subscription plan of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateUserCommand(
    String name,
    String email,
    String password,
    String phone,
    String role,
    String plan
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateUserCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("User password cannot be null or empty");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("User role cannot be null or empty");
        }
        if (plan == null || plan.isBlank()) {
            throw new IllegalArgumentException("User plan cannot be null or empty");
        }
        // phone is optional, no validation required
    }
}


