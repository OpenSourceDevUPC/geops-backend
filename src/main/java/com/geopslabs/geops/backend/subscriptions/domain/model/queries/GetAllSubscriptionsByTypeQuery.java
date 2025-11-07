package com.geopslabs.geops.backend.subscriptions.domain.model.queries;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

/**
 * GetAllSubscriptionsByTypeQuery
 *
 * Query record to retrieve all subscriptions with a specific type.
 * This query is useful for filtering subscriptions by BASIC or PREMIUM type.
 *
 * @summary Query to retrieve all subscriptions with a specific type
 * @param type The subscription type to filter by (BASIC or PREMIUM)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetAllSubscriptionsByTypeQuery(SubscriptionType type) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetAllSubscriptionsByTypeQuery {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
    }
}
