package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * AuthenticationResource
 *
 * Resource Resource for authentication responses via REST API
 * This resource represents the response payload containing user information after authentication
 *
 * @summary Response resource for authentication
 * @param id The unique identifier of the user
 * @param name The full name of the user
 * @param email The email address of the user
 * @param phone The phone number of the user
 * @param role The role of the user in the system
 * @param plan The subscription plan of the user
 * @param message Authentication success message
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record AuthenticationResource(
    Long id,
    String name,
    String email,
    String phone,
    String role,
    String plan,
    String token,
    String message
) {
}

