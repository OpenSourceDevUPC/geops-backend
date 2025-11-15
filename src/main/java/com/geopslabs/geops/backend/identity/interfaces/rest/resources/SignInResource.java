package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * SignInResource
 *
 * Resource DTO for user authentication via REST API
 * This resource represents the request payload for user sign-in
 *
 * @summary Request resource for user authentication
 * @param email The email address of the user
 * @param password The password for authentication
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record SignInResource(
    String email,
    String password
) {
}

