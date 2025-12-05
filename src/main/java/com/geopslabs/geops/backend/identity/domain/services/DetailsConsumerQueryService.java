package com.geopslabs.geops.backend.identity.domain.services;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetDetailsConsumerByUserIdQuery;

import java.util.Optional;

/**
 * DetailsConsumerQueryService
 *
 * Service interface for handling consumer details query operations
 * This service defines methods for retrieving consumer details information
 * following the DDD pattern
 *
 * @summary Service for consumer details query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface DetailsConsumerQueryService {

    /**
     * Handles the query to get consumer details by user ID
     *
     * @param query The GetDetailsConsumerByUserIdQuery containing the user ID
     * @return An Optional containing the consumer details if found, empty otherwise
     */
    Optional<DetailsConsumer> handle(GetDetailsConsumerByUserIdQuery query);
}

