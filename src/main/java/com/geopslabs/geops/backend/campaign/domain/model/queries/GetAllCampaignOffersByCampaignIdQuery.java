package com.geopslabs.geops.backend.campaign.domain.model.queries;

/**
 * Query to get all the campaigns offers by the campaign id
 * @param campaignId The campaign Id
 */
public record GetAllCampaignOffersByCampaignIdQuery(Long campaignId) {
}
