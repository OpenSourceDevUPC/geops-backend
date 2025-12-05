package com.geopslabs.geops.backend.campaign.interfaces.rest.transform;

import com.geopslabs.geops.backend.campaign.domain.model.commands.UpdateCampaignCommand;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.UpdateCampaignResource;

public class UpdateCampaignCommandFromResourceAssembler {
    public static UpdateCampaignCommand toCommandFromResource(Long id, UpdateCampaignResource resource) {
        return new UpdateCampaignCommand(id, resource.name(), resource.description(), resource.startDate(),
                resource.endDate(), resource.status());
    }
}
