package com.geopslabs.geops.backend.offers.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * CreateOfferResource
 * Resource Resource for creating offers via REST API.
 * This resource represents the request payload for offer creation.
 *
 * @summary Request resource for creating offers
 * @param title The title of the offer
 * @param partner The partner providing the offer
 * @param price The price of the offer
 * @param codePrefix The prefix code for the offer
 * @param validTo The expiration date of the offer
 * @param rating The rating of the offer (0-5)
 * @param location The location where the offer is valid
 * @param category The category of the offer
 * @param imageUrl The URL of the offer image (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateOfferResource(
    Long campaignId,
    String title,
    String partner,
    BigDecimal price,
    String codePrefix,
    LocalDate validTo,
    Integer rating,
    String location,
    String category,
    String imageUrl)
{
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateOfferResource {
        if(campaignId == null || campaignId < 0)
            throw new IllegalArgumentException("Campaign id cannot be null or negative");

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
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }

        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("location cannot be null or empty");
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category cannot be null or empty");
        }
    }
}
