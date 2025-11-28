package com.geopslabs.geops.backend.campaign.domain.model.commands;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import java.util.Date;

/**
 * Command to create a campaign
 * @param campaignName The campaign Name
 * @param startDate The start date of the campaign
 * @param endDate The end date of the campaign
 * @param estimatedBudget The estimated budget of the campaign
 * @see Campaign
 */
public record CreateCampaignCommand(String campaignName, Date startDate, Date endDate, float estimatedBudget) {
}
