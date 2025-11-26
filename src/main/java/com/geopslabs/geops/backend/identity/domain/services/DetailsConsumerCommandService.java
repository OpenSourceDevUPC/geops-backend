package com.geopslabs.geops.backend.identity.domain.services;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import com.geopslabs.geops.backend.identity.domain.model.commands.CreateDetailsConsumerCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateDetailsConsumerCommand;

import java.util.Optional;

/**
 * DetailsConsumerCommandService
 *
 * Service interface for handling consumer details command operations
 * This service defines methods for creating and updating consumer details
 * following the DDD pattern
 *
 * @summary Service for consumer details command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface DetailsConsumerCommandService {

    /**
     * Handles the command to create new consumer details
     *
     * @param command The CreateDetailsConsumerCommand containing consumer details data
     * @return An Optional containing the created consumer details if successful, empty otherwise
     */
    Optional<DetailsConsumer> handle(CreateDetailsConsumerCommand command);

    /**
     * Handles the command to update existing consumer details
     *
     * @param command The UpdateDetailsConsumerCommand containing updated consumer details data
     * @return An Optional containing the updated consumer details if successful, empty otherwise
     */
    Optional<DetailsConsumer> handle(UpdateDetailsConsumerCommand command);
}

