package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * CreateDetailsOwnerResource
 *
 * Resource Resource for creating owner details via REST API
 * This resource represents the request payload for creating new owner details
 *
 * @summary Request resource for creating owner details
 * @param businessName Business name of the owner
 * @param businessType Type of business
 * @param taxId Tax identification number
 * @param website Business website URL
 * @param description Detailed business description
 * @param address Main physical address of the business
 * @param horarioAtencion Business operating hours/schedule
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateDetailsOwnerResource(
    String businessName,
    String businessType,
    String taxId,
    String website,
    String description,
    String address,
    String horarioAtencion
) {
}

