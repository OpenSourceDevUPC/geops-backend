package com.geopslabs.geops.backend.campaign.domain.services;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignOffersByCampaignIdQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetCampaignByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Campaign Query service interface to use all the queries
 */
public interface CampaignQueryService {

    Optional<Campaign> handle(GetCampaignByIdQuery query);

    Optional<List<Campaign>> handle(GetAllCampaignsQuery query);
}
