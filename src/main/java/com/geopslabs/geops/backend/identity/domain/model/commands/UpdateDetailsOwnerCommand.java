package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * UpdateDetailsOwnerCommand
 *
 * Command record for updating owner details
 * This command allows partial updates - null fields will not be updated
 *
 * @summary Command to update owner details
 * @param userId The unique identifier of the user
 * @param businessName Updated business name (optional)
 * @param businessType Updated business type (optional)
 * @param taxId Updated tax ID (optional)
 * @param website Updated website (optional)
 * @param description Updated description (optional)
 * @param address Updated address (optional)
 * @param horarioAtencion Updated operating hours (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateDetailsOwnerCommand(
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
    public UpdateDetailsOwnerCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

