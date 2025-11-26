package com.geopslabs.geops.backend.identity.domain.services;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import com.geopslabs.geops.backend.identity.domain.model.commands.CreateDetailsOwnerCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateDetailsOwnerCommand;

import java.util.Optional;

/**
 * DetailsOwnerCommandService
 *
 * Service interface for handling owner details command operations
 * This service defines methods for creating and updating owner details
 * following the DDD pattern
 *
 * @summary Service for owner details command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface DetailsOwnerCommandService {

    /**
     * Handles the command to create new owner details
     *
     * @param command The CreateDetailsOwnerCommand containing owner details data
     * @return An Optional containing the created owner details if successful, empty otherwise
     */
    Optional<DetailsOwner> handle(CreateDetailsOwnerCommand command);

    /**
     * Handles the command to update existing owner details
     *
     * @param command The UpdateDetailsOwnerCommand containing updated owner details data
     * @return An Optional containing the updated owner details if successful, empty otherwise
     */
    Optional<DetailsOwner> handle(UpdateDetailsOwnerCommand command);
}

