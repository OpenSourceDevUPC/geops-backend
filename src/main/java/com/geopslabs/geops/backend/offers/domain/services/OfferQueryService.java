package com.geopslabs.geops.backend.offers.domain.services;

import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersByCampaignIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOffersByIdsQuery;

import java.util.List;
import java.util.Optional;

/**
 * OfferQueryService
 * Domain service interface that defines query operations for offers.
 * This service handles all GET operations
 *
 * @summary Service interface for handling offer query operations
 * @since 4.0
 * @author GeOps Labs
 */
public interface OfferQueryService {

    /**
     * Handles the creation of a list containing all the offers in the database
     * @param query Contains nothing (CQRS design)
     * @return A list with all the offers from the database
     */
    List<Offer> handle(GetAllOffersQuery query);

    /**
     * Function to get an offer from the database using its id
     * @param query The query containing the offer unique id
     * @return An offer with the given ID
     */
    Optional<Offer> handle(GetOfferByIdQuery query);

    /**
     * Handles the creation of a list containing offers from a list of ids
     * @param query The query containing the list of ids
     * @return A list of offers from the given list of ids
     */
    List<Offer> handle(GetOffersByIdsQuery query);

    /**
     * Handles the creation of a list containing offers from a campaign
     * @param query The query containing the campaign id
     * @return A list of offers from a campaign
     */
    List<Offer> handle(GetAllOffersByCampaignIdQuery query);
}
