package com.geopslabs.geops.backend.campaign.application.internal.commandservices;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CampaignCommandServiceImpl implements CampaignCommandService {

    @Override
    public Optional<Campaign> handle(CreateCampaignCommand command) {

    }
}
