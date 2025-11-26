package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * SignUpResource
 *
 * Resource DTO for user registration via REST API
 * This resource represents the request payload for user sign-up
 *
 * @summary Request resource for user registration
 * @param name The full name of the user
 * @param email The email address of the user
 * @param phone The phone number of the user
 * @param password The password for authentication
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record SignUpResource(
    String name,
    String email,
    String phone,
    String password
) {
}

