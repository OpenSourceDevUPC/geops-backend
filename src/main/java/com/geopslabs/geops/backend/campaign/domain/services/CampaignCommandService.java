package com.geopslabs.geops.backend.campaign.domain.services;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.DeleteCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.UpdateCampaignCommand;

import java.util.Optional;

/**
 * Campaign Command service interface to use all the commands
 */
public interface CampaignCommandService {

    Optional<Campaign> handle(CreateCampaignCommand command);

    Optional<Campaign> handle(UpdateCampaignCommand command);

    boolean handle(DeleteCampaignCommand command);
}
