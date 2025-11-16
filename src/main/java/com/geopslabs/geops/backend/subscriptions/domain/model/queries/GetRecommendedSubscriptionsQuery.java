package com.geopslabs.geops.backend.subscriptions.domain.model.queries;

/**
 * GetRecommendedSubscriptionsQuery
 *
 * Query record to retrieve all recommended subscription plans.
 * This query is useful for displaying recommended plans to users.
 *
 * @summary Query to retrieve all recommended subscription plans
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetRecommendedSubscriptionsQuery() {
    // This query doesn't require any parameters as it retrieves all recommended subscriptions
    // The logic is handled in the repository/service layer by filtering
    // subscriptions where recommended = true
}
