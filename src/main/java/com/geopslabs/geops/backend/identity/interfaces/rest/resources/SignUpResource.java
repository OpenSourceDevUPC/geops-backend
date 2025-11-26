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
 * @param password The password for authentication
 * @param phone The phone number of the user (optional)
 * @param role The role of the user (CONSUMER or OWNER), defaults to CONSUMER if not provided
 * @param plan The subscription plan (BASIC or PREMIUM), defaults to BASIC if not provided
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record SignUpResource(
    String name,
    String email,
    String password,
    String phone,
    String role,
    String plan
) {
}


