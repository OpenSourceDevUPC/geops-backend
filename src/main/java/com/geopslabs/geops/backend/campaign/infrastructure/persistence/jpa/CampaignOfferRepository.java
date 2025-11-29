package com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.CampaignOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignOfferRepository extends JpaRepository<CampaignOffer, CampaignOfferId> {
    Optional<List<CampaignOffer>> findAllById_CampaignId(Long idCampaignId);

    Optional<CampaignOffer> findCampaignOfferById_OfferIdAndId_CampaignId(Long idOfferId, Long idCampaignId);



}
