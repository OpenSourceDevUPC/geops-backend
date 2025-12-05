package com.geopslabs.geops.backend.offers.domain.model.commands;

import java.math.BigDecimal;

/**
 * CreateOfferCommand
 * Command record that encapsulates all the necessary data to create a new offer.
 * This command validates input data to ensure all required fields are provided
 * and meet the business rules for offer creation
 *
 * @summary Command to create a new offer
 * @param campaignId The campaign id
 * @param title The title of the offer
 * @param partner The partner company providing the offer
 * @param price The price of the offer
 * @param codePrefix The code prefix for promotional codes
 * @param validTo The expiration date of the offer
 * @param rating The rating of the offer (0-5 scale)
 * @param location The location where the offer is valid
 * @param category The category of the offer
 * @param imageUrl The URL of the offer's display image
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateOfferCommand (
        Long campaignId,
        String title,
        String partner,
        java.math.BigDecimal price,
        String codePrefix,
        java.time.LocalDate validTo,
        Integer rating,
        String location,
        String category,
        String imageUrl
){
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateOfferCommand {

        if(campaignId == null || campaignId < 1){
            throw new IllegalArgumentException("campaignId cannot be null or less than 1");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title cannot be null or empty");
        }

        if (partner == null || partner.isBlank()) {
            throw new IllegalArgumentException("partner cannot be null or empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must be positive");
        }

        if (codePrefix == null || codePrefix.isBlank()) {
            throw new IllegalArgumentException("codePrefix cannot be null or empty");
        }

        if (validTo == null) {
            throw new IllegalArgumentException("validTo cannot be null");
        }

        if (rating == null || rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }

        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location cannot be null or empty");
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category cannot be null or empty");
        }
    }
}
