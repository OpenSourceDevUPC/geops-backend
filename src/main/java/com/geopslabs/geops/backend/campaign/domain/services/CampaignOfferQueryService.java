package com.geopslabs.geops.backend.campaign.domain.services;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignOffersByCampaignIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Campaign Offer Query service interface to use all the queries
 */
public interface CampaignOfferQueryService {

    Optional<List<CampaignOffer>> handle(GetAllCampaignOffersByCampaignIdQuery query);

}
