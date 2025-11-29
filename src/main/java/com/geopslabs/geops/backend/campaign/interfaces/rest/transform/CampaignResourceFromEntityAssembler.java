package com.geopslabs.geops.backend.campaign.interfaces.rest.transform;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.CampaignResource;

public class CampaignResourceFromEntityAssembler {
    public static CampaignResource toResourceFromEntity(Campaign entity)
    {
        return new CampaignResource(entity.getId(),entity.getName(),entity.getDescription(),entity.getStartDate(),
                entity.getEndDate(), entity.getStatus().toString(), entity.getEstimatedBudget(),
                entity.getTotalImpressions(),entity.getTotalClicks(),entity.getCTR(),entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
