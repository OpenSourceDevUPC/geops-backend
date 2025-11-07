package com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

import java.math.BigDecimal;

/**
 * SubscriptionResource
 *
 * Resource DTO for subscription plan responses via REST API.
 * This resource represents the response payload containing subscription plan information
 * matching the frontend interface structure: { id, price, recommended, type }
 *
 * @summary Response resource for subscription plan data
 * @param id The unique identifier of the subscription plan
 * @param price The price of the subscription plan
 * @param recommended Whether this subscription plan is recommended
 * @param type The type of subscription plan (BASIC or PREMIUM)
 * @param createdAt Timestamp when the subscription plan was created
 * @param updatedAt Timestamp when the subscription plan was last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record SubscriptionResource(
    Long id,
    BigDecimal price,
    Boolean recommended,
    SubscriptionType type,
    String createdAt,
    String updatedAt
) {
    // This record doesn't need validation in the compact constructor
    // as it's used for response data that should already be validated
}
