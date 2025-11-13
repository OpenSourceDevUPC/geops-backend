package com.geopslabs.geops.backend.offers.domain.model.commands;

/**
 * UpdateOfferCommand
 *
 * Command record for updating an existing offer in the GeOps platform
 * This command encapsulates all necessary fields to modify an offer's details
 *
 * @summary Command to update an existing offer
 * @param id The unique identifier of the offer to update
 * @param title The updated title of the offer
 * @param partner The updated partner associated with the offer
 * @param price The updated price of the offer
 * @param codePrefix The updated code prefix for the offer
 * @param validTo The updated validity date of the offer
 * @param rating The updated rating of the offer
 * @param location The updated location of the offer
 * @param category The updated category of the offer
 * @param imageUrl The updated image URL for the offer
 *
 * @since 1.0
 * @author GeOps Labs
 */

public record UpdateOfferCommand (
        Long id,
        String title,
        String partner,
        java.math.BigDecimal price,
        String codePrefix,
        java.time.LocalDate validTo,
        Integer rating,
        String location,
        String category,
        String imageUrl
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateOfferCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
}
