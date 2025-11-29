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
}
