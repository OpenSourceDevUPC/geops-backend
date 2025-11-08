package com.geopslabs.geops.backend.offers.domain.services;

import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOffersByIdsQuery;

import java.util.List;
import java.util.Optional;


public interface OfferQueryService {

    List<Offer> handle(GetAllOffersQuery query);

    Optional<Offer> handle(GetOfferByIdQuery query);

    List<Offer> handle(GetOffersByIdsQuery query);
}
