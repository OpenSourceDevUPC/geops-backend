package com.geopslabs.geops.backend.campaign.domain.model.commands;

/**
 * Command to add a campaign offer to a campaign
 * @param campaignId The campaign Id
 * @param offerImpressions The offer Impressions, must be 0 when creating the campaign offer
 * @param offerClicks The offer clicks, must be 0 when creating the campaign offer
 * @param offerCTR The offer CTR, calculated with the impressions and clicks
 */
public record AddCampaignOfferToCampaignCommand(Long campaignId, Long offerImpressions,
                                                Long offerClicks, Long offerCTR) {
}
