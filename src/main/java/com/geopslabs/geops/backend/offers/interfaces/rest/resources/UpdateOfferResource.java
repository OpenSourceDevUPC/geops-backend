package com.geopslabs.geops.backend.offers.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * UpdateOfferResource
 *
 * Resource Resource for updating offers via REST API
 * This resource represents the request payload for offer updates
 * All fields are optional except for the ID (provided via path variable)
 *
 * @summary Request resource for updating offers
 * @param title The title of the offer (optional)
 * @param partner The partner providing the offer (optional)
 * @param price The price of the offer (optional)
 * @param codePrefix The prefix code for the offer (optional)
 * @param validTo The expiration date of the offer (optional)
 * @param rating The rating of the offer (0-5) (optional)
 * @param location The location where the offer is valid (optional)
 * @param category The category of the offer (optional)
 * @param imageUrl The URL of the offer image (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateOfferResource(
    String title,
    String partner,
    BigDecimal price,
    String codePrefix,
    LocalDate validTo,
    Integer rating,
    String location,
    String category,
    String imageUrl
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateOfferResource {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price must be positive if provided");
        }

        if (rating != null && (rating < 0 || rating > 5)) {
            throw new IllegalArgumentException("rating must be between 0 and 5 if provided");
        }

        if (title != null && title.isBlank()) {
            throw new IllegalArgumentException("title cannot be blank if provided");
        }

        if (partner != null && partner.isBlank()) {
            throw new IllegalArgumentException("partner cannot be blank if provided");
        }

        if (codePrefix != null && codePrefix.isBlank()) {
            throw new IllegalArgumentException("codePrefix cannot be blank if provided");
        }

        if (location != null && location.isBlank()) {
            throw new IllegalArgumentException("location cannot be blank if provided");
        }

        if (category != null && category.isBlank()) {
            throw new IllegalArgumentException("category cannot be blank if provided");
        }
    }
}
