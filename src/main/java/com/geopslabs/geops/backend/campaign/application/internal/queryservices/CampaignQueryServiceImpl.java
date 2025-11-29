package com.geopslabs.geops.backend.campaign.application.internal.queryservices;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetCampaignByIdQuery;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignQueryService;
import com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignQueryServiceImpl implements CampaignQueryService {

    private final CampaignRepository campaignRepository;

    public CampaignQueryServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Optional<Campaign> handle(GetCampaignByIdQuery query) {
        try{
            var campaign = campaignRepository.findCampaignById(query.id());
            if(campaign.isEmpty())
                throw new IllegalArgumentException("Campaign with id " + query.id() + " not found");
            return campaign;
        }
        catch (Exception e){
            System.out.println("Error creating a campaign: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Campaign> handle(GetAllCampaignsQuery query) {return campaignRepository.findAll();}
}
