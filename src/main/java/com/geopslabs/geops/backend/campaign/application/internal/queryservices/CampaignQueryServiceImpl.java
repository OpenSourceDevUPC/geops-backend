package com.geopslabs.geops.backend.campaign.application.internal.queryservices;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsByUserIdQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetCampaignByIdQuery;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignQueryService;
import com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa.CampaignRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignQueryServiceImpl implements CampaignQueryService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public CampaignQueryServiceImpl(CampaignRepository campaignRepository,  UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<Campaign> handle(GetAllCampaignsByUserIdQuery query) {
        if (!userRepository.existsById(query.userId()))
            throw new IllegalArgumentException("User with id " + query.userId() + " not found");
        return campaignRepository.findAllByUser_Id(query.userId());
    }
}
