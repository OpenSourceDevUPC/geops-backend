package com.geopslabs.geops.backend.campaign.application.internal.commandservices;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignCommandService;
import com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CampaignCommandServiceImpl implements CampaignCommandService {

    private final CampaignRepository campaignRepository;

    public CampaignCommandServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> handle(CreateCampaignCommand command) {
        try{
            var campaign = new Campaign(command);

            var savedCampaign = campaignRepository.save(campaign);

            return Optional.of(savedCampaign);
        }
        catch(Exception e){
            System.out.println("Error creating a campaign: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
