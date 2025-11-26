package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * CreateDetailsOwnerCommand
 *
 * Command record for creating owner details for a user
 *
 * @summary Command to create owner details
 * @param userId The unique identifier of the user
 * @param businessName The business name
 * @param businessType The type of business
 * @param taxId The tax identification number
 * @param website The business website
 * @param description The business description
 * @param address The business address
 * @param horarioAtencion The operating hours
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateDetailsOwnerCommand(
    Long userId,
    String businessName,
    String businessType,
    String taxId,
    String website,
    String description,
    String address,
    String horarioAtencion
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateDetailsOwnerCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (businessName == null || businessName.isBlank()) {
            throw new IllegalArgumentException("Business name is required");
        }
    }
}

