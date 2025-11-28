package com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.CampaignOfferId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignOfferRepository extends JpaRepository<CampaignOffer, CampaignOfferId> {

}
