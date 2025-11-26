package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * DetailsOwnerResource
 *
 * Resource DTO for owner details responses via REST API
 * This resource represents the response payload containing owner details information
 *
 * @summary Response resource for owner details data
 * @param id The unique identifier (same as user ID)
 * @param userId The user ID associated with these owner details
 * @param businessName Business name of the owner
 * @param businessType Type of business
 * @param taxId Tax identification number
 * @param website Business website URL
 * @param description Detailed business description
 * @param address Main physical address of the business
 * @param horarioAtencion Business operating hours/schedule
 * @param createdAt Timestamp when the owner details were created
 * @param updatedAt Timestamp when the owner details were last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DetailsOwnerResource(
    Long id,
    Long userId,
    String businessName,
    String businessType,
    String taxId,
    String website,
    String description,
    String address,
    String horarioAtencion,
    java.util.Date createdAt,
    java.util.Date updatedAt
) {
}

