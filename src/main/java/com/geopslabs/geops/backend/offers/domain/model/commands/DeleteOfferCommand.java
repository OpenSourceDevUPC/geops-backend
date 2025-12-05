package com.geopslabs.geops.backend.offers.domain.model.commands;

/**
 * Deletes an offer from the database using its id
 * @param id The offer unique identification
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteOfferCommand(Long id) {
    public DeleteOfferCommand {
        if(id == null || id < 1)
            throw new IllegalArgumentException("Offer Id cannot be null or less than 1");
    }
}
