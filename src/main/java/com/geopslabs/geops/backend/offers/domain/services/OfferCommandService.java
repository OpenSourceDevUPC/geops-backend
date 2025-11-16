package com.geopslabs.geops.backend.offers.domain.services;

import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.domain.model.commands.CreateOfferCommand;
import com.geopslabs.geops.backend.offers.domain.model.commands.UpdateOfferCommand;

import java.util.Optional;

/**
 * OfferCommandService
 *
 * Domain service interface that defines command operations for offers.
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern
 *
 * @summary Service interface for handling offer command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface OfferCommandService {

    /**
     * Handles the creation of a new offer
     *
     * This method processes the command to create a new offer,
     * validates the input, and persists the offer data
     *
     * @param command The command containing all necessary data for offer creation
     * @return An Optional containing the created Offer if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Offer> handle(CreateOfferCommand command);

    /**
     * Handles the update of an existing offer
     *
     * This method processes the command to update offer data such as title,
     * partner, price, validity dates, and other offer attributes
     * It performs partial updates based on provided fields
     *
     * @param command The command containing the offer ID and updated data
     * @return An Optional containing the updated Offer if successful, empty if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Offer> handle(UpdateOfferCommand command);

    /**
     * Handles the deletion of an offer by its unique identifier
     *
     * This method processes the deletion of an offer from the system
     * It's a soft or hard delete depending on business requirements
     *
     * @param id The unique identifier of the offer to delete
     * @return true if the offer was successfully deleted, false if not found
     * @throws IllegalArgumentException if the ID is invalid
     */
    boolean handleDelete(Long id);
}
