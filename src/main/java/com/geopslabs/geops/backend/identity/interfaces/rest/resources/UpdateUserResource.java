package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * UpdateUserResource
 *
 * Resource DTO for updating an existing user via REST API
 * This resource represents the request payload for user updates
 * All fields are optional - only provided fields will be updated
 *
 * @summary Request resource for updating a user
 * @param name The updated name (optional)
 * @param email The updated email (optional)
 * @param phone The updated phone (optional)
 * @param role The updated role (optional)
 * @param plan The updated subscription plan (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateUserResource(
    String name,
    String email,
    String phone,
    String role,
    String plan
) {
}

