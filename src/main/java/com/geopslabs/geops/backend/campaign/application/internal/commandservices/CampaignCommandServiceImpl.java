package com.geopslabs.geops.backend.campaign.application.internal.commandservices;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.DeleteCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.commands.UpdateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.ECampaignStatus;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignCommandService;
import com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa.CampaignRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CampaignCommandServiceImpl implements CampaignCommandService {

    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public CampaignCommandServiceImpl(CampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Campaign> handle(CreateCampaignCommand command) {
        try{
            //Verifies if user is found by a user id
            var foundUser = userRepository.findById(command.userId());
            if(foundUser.isEmpty()) return Optional.empty();
            //Verifies if user is OWNER role
            if(!Objects.equals(foundUser.get().getRole(), "OWNER"))
                throw new IllegalArgumentException("The user does not have OWNER role");

            var campaign = new Campaign(foundUser.get(),command);

            var savedCampaign = campaignRepository.save(campaign);

            return Optional.of(savedCampaign);
        }
        catch(Exception e){
            System.out.println("Error creating a campaign: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Campaign> handle(UpdateCampaignCommand command) {
        try{
            var foundCampaign = campaignRepository.findCampaignById(command.id());
            if(foundCampaign.isEmpty()) throw new NoSuchElementException("Campaign not found with id: " + command.id());
            foundCampaign.get().edit(command.name(), command.description(), command.startDate(), command.endDate(),
                    ECampaignStatus.valueOf(command.status()));
            var editedCampaign = campaignRepository.save(foundCampaign.get());
            return Optional.of(editedCampaign);
        }
        catch(Exception e){
            System.out.println("Error editing campaign: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeleteCampaignCommand command) {
        try{
            var foundCampaign = campaignRepository.findCampaignById(command.id());
            if(foundCampaign.isEmpty()) throw new NoSuchElementException("Campaign not found with id: " + command.id());
            campaignRepository.deleteCampaignById(command.id());
            return true;
        }
        catch(Exception e){
            System.out.println("Error deleting campaign: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
