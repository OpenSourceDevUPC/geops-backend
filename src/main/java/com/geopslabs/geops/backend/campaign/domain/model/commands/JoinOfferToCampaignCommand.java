package com.geopslabs.geops.backend.campaign.domain.model.commands;

/**
 * Command to join an offer to a campaign
 * Administration-use only
 * @param offerId The offer identification
 * @param campaignId The campaign identification
 */
public record JoinOfferToCampaignCommand(Long offerId, Long campaignId) {
}
