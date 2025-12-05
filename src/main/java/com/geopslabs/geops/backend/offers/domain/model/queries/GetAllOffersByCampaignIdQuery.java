package com.geopslabs.geops.backend.offers.domain.model.queries;

/**
 * GetAllOffersByCampaignIdQuery
 * Query record to retrieve all offers from a campaign.
 * This query is really important for provider page
 *
 * @param campaignId The campaign unique identification
 * @summary Query to retrieve all offers from a campaign
 * @since 3.0
 * @author GeOps Labs
 */
public record GetAllOffersByCampaignIdQuery(Long campaignId) {
    public GetAllOffersByCampaignIdQuery{
        if(campaignId == null || campaignId < 1)
            throw new  IllegalArgumentException("Campaign Id cannot be null or less than 1");
    }
}
