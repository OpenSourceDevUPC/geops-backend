package com.geopslabs.geops.backend.campaign.interfaces.rest.transform;

import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.CreateCampaignResource;

public class CreateCampaignCommandFromResourceAssembler {
    public static CreateCampaignCommand toCommandFromResource(CreateCampaignResource resource) {
        return new CreateCampaignCommand(resource.userId(), resource.name(), resource.description(), resource.startDate(),
                resource.endDate(), resource.estimatedBudget());
    }
}
