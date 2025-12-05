package com.geopslabs.geops.backend.identity.domain.services;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetDetailsOwnerByUserIdQuery;

import java.util.Optional;

/**
 * DetailsOwnerQueryService
 *
 * Service interface for handling owner details query operations
 * This service defines methods for retrieving owner details information
 * following the DDD pattern
 *
 * @summary Service for owner details query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface DetailsOwnerQueryService {

    /**
     * Handles the query to get owner details by user ID
     *
     * @param query The GetDetailsOwnerByUserIdQuery containing the user ID
     * @return An Optional containing the owner details if found, empty otherwise
     */
    Optional<DetailsOwner> handle(GetDetailsOwnerByUserIdQuery query);
}

