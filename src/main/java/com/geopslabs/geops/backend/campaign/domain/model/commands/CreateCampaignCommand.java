package com.geopslabs.geops.backend.campaign.domain.model.commands;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;

import java.time.LocalDate;

/**
 * Command to create a campaign
 * @param name The campaign name
 * @param description The campaign description
 * @param startDate The start date of the campaign
 * @param endDate The end date of the campaign
 * @see Campaign
 */
public record CreateCampaignCommand(String name, String description, LocalDate startDate, LocalDate endDate) {
    public CreateCampaignCommand {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Campaign name cannot be null or blank");

        if(description == null || description.isBlank())
            throw new IllegalArgumentException("Campaign description cannot be null or blank");

        if(startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if(endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if(endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");
    }
}
