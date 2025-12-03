package com.geopslabs.geops.backend.offers.application.internal.queryservices;

import com.geopslabs.geops.backend.campaign.infrastructure.persistence.jpa.CampaignRepository;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersByCampaignIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOffersByIdsQuery;
import com.geopslabs.geops.backend.offers.domain.services.OfferQueryService;
import com.geopslabs.geops.backend.offers.infrastructure.persistence.jpa.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * OfferQueryServiceImpl
 * Implementation of the OfferQueryService that handles all query operations
 * for offers. This service implements the business logic for retrieving and
 * searching offers following DDD principles
 *
 * @summary Implementation of offer query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class OfferQueryServiceImpl implements OfferQueryService {

    private final OfferRepository offerRepository;
    private final CampaignRepository campaignRepository;

    /**
     * Constructor for dependency injection
     *
     * @param offerRepository The repository for offer data access
     * @param campaignRepository The repository for campaign data access
     */
    public OfferQueryServiceImpl(OfferRepository offerRepository, CampaignRepository campaignRepository) {
        this.offerRepository = offerRepository;
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<Offer> handle(GetAllOffersQuery query) {
        try {
            return offerRepository.findAll();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving all offers: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Offer> handle(GetOfferByIdQuery query) {
        try {
            return offerRepository.findById(query.id());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving offer by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> handle(GetOffersByIdsQuery query) {
        try {
            return offerRepository.findByIdIn(query.ids());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving offers by IDs: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> handle(GetAllOffersByCampaignIdQuery query) {
        try{
            var existingCampaign = campaignRepository.findCampaignById(query.campaignId());
            if (existingCampaign.isEmpty())
                throw new IllegalArgumentException("Campaign with id " + query.campaignId() + " does not exist");
            return offerRepository.findByCampaign_Id(query.campaignId());
        }
        catch (Exception e){
            System.err.println("Error retrieving all offers by campaign id: " + e.getMessage());
            return List.of();
        }
    }
}
