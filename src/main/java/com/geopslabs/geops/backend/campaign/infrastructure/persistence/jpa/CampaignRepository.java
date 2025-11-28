package com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
