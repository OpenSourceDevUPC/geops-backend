package com.geopslabs.geops.backend.campaign.domain.services;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.commands.AddCampaignOfferToCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.JoinOfferToCampaignCommand;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;

import java.util.Optional;

/**
 * Campaign Command service interface to use all the commands
 */
public interface CampaignCommandService {

    Optional<Campaign> handle(CreateCampaignCommand command);

    Optional<CampaignOffer> handle(AddCampaignOfferToCampaignCommand command);

    Optional<Campaign> handle(JoinOfferToCampaignCommand command);
}
