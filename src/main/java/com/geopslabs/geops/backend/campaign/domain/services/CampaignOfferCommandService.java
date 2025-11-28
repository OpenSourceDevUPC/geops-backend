package com.geopslabs.geops.backend.campaign.domain.services;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.commands.AddCampaignOfferToCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.JoinOfferToCampaignCommand;

import java.util.Optional;

/**
 * Campaign Offer Command service interface to use all the commands
 */
public interface CampaignOfferCommandService {
    Optional<CampaignOffer> handle(AddCampaignOfferToCampaignCommand command);

    Optional<Campaign> handle(JoinOfferToCampaignCommand command);

}
