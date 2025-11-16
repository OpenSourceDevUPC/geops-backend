package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * CreateUserResource
 *
 * Resource DTO for creating a new user via REST API
 * This resource represents the request payload for user registration
 *
 * @summary Request resource for creating a user
 * @param name The full name of the user
 * @param email The email address of the user
 * @param password The password for authentication
 * @param role The role of the user in the system
 * @param plan The subscription plan of the user
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateUserResource(
    String name,
    String email,
    String password,
    String role,
    String plan
) {
}


