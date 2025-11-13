package com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

import java.math.BigDecimal;

/**
 * CreateSubscriptionResource
 *
 * Resource DTO for creating subscription plans via REST API.
 * This resource represents the request payload for subscription plan creation,
 * matching the frontend interface structure: { id, price, recommended, type }
 *
 * @summary Request resource for creating subscription plans
 * @param price The price of the subscription plan
 * @param recommended Whether this subscription plan is recommended
 * @param type The type of subscription plan (BASIC or PREMIUM)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateSubscriptionResource(
    BigDecimal price,
    Boolean recommended,
    SubscriptionType type
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateSubscriptionResource {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be null or negative");
        }

        if (recommended == null) {
            throw new IllegalArgumentException("recommended cannot be null");
        }

        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
    }
}

