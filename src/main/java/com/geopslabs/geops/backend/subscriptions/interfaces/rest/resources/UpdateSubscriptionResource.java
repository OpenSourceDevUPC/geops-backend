package com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

import java.math.BigDecimal;

/**
 * UpdateSubscriptionResource
 *
 * Resource Resource used for updating a subscription plan via REST API.
 * Fields are nullable to support partial updates. Do not perform strict validation
 * here; validation is performed by application/command layer if needed.
 *
 * @summary Request resource for updating subscription plans
 * @param price The new price (optional)
 * @param recommended Whether this subscription plan is recommended (optional)
 * @param type The type of subscription plan (optional)
 */
public record UpdateSubscriptionResource(
    BigDecimal price,
    Boolean recommended,
    SubscriptionType type
) {
    // Intentionally no validation in compact constructor to allow partial updates
}

