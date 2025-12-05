package com.geopslabs.geops.backend.offers.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * OfferResource
 * Resource Resource for offer responses via REST API
 * This resource represents the response payload containing offer information
 *
 * @summary Response resource for offer data
 * @param id The unique identifier of the offer (database ID)
 * @param campaignId The unique identifier of the campaign
 * @param title The title of the offer
 * @param partner The partner providing the offer
 * @param price The price of the offer
 * @param codePrefix The prefix code for the offer
 * @param validTo The expiration date of the offer
 * @param rating The rating of the offer (0-5)
 * @param location The location where the offer is valid
 * @param category The category of the offer
 * @param imageUrl The URL of the offer image
 * @param createdAt Timestamp when the offer was created
 * @param updatedAt Timestamp when the offer was last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record OfferResource(
    Long id,
    Long campaignId,
    String title,
    String partner,
    BigDecimal price,
    String codePrefix,
    LocalDate validTo,
    Integer rating,
    String location,
    String category,
    String imageUrl,
    String createdAt,
    String updatedAt
) {
}
