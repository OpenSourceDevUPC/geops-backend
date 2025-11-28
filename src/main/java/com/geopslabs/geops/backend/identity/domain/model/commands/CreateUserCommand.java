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
 * @param phone The phone number of the user (unique identifier)
 * @param password The encrypted password for authentication
 * @param role The role of the user in the system (CONSUMER or SUPPLIER)
 * @param plan The subscription plan of the user (BASIC or PREMIUM)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateUserCommand(
    String name,
    String email,
    String phone,
    String password,
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
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone format");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        if (plan == null || plan.isBlank()) {
            throw new IllegalArgumentException("Plan cannot be null or empty");
        }
    }

    /**
     * Validates email format
     *
     * @param email The email to validate
     * @return true if the email format is valid
     */
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Validates phone format (international format)
     *
     * @param phone The phone to validate
     * @return true if the phone format is valid
     */
    private static boolean isValidPhone(String phone) {
        // Acepta formatos: +51999999999, 999999999, +1-555-555-5555
        String phoneRegex = "^[+]?[0-9]{1,4}?[-\\s]?[(]?[0-9]{1,4}[)]?[-\\s]?[0-9]{1,4}[-\\s]?[0-9]{1,9}$";
        return phone.matches(phoneRegex);
    }
}

