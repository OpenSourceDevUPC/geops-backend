package com.geopslabs.geops.backend.subscriptions.domain.model.queries;

/**
 * GetSubscriptionByIdQuery
 *
 * Query record to retrieve a subscription by its unique identifier.
 * This query is used to fetch specific subscription details when needed
 * for validation, status checking, or displaying subscription information.
 *
 * @summary Query to retrieve a subscription by its ID
 * @param subscriptionId The unique identifier of the subscription
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetSubscriptionByIdQuery(Long subscriptionId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetSubscriptionByIdQuery {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscriptionId cannot be null or negative");
        }
    }
}
