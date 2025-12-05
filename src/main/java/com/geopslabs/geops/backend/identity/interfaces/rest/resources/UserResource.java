package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * UserResource
 *
 * Resource DTO for user responses via REST API
 * This resource represents the response payload containing user information
 * Note: Password is excluded from the response for security reasons
 *
 * @summary Response resource for user data
 * @param id The unique identifier of the user (database ID)
 * @param name The full name of the user
 * @param email The email address of the user
 * @param phone The phone number of the user
 * @param role The role of the user in the system
 * @param plan The subscription plan of the user
 * @param createdAt Timestamp when the user was created
 * @param updatedAt Timestamp when the user was last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UserResource(
    Long id,
    String name,
    String email,
    String phone,
    String role,
    String plan,
    java.util.Date createdAt,
    java.util.Date updatedAt
) {
}

