package com.geopslabs.geops.backend.subscriptions.domain.services;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetAllSubscriptionsByTypeQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetAllSubscriptionsQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetRecommendedSubscriptionsQuery;

import java.util.List;
import java.util.Optional;

/**
 * SubscriptionQueryService
 *
 * Domain service interface that defines query operations for subscription plans.
 * This service handles all read operations following the Command Query Responsibility
 * Segregation (CQRS) pattern, providing various ways to retrieve subscription data.
 * Based on the simple subscription structure: { id, price, recommended, type }
 *
 * @summary Service interface for handling subscription plan query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface SubscriptionQueryService {

    /**
     * Handles the query to retrieve a subscription by its unique identifier.
     *
     * This method processes the query to find a specific subscription using its ID.
     * It's commonly used for subscription validation and detailed views.
     *
     * @param query The query containing the subscription ID
     * @return An Optional containing the Subscription if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);

    /**
     * Handles the query to retrieve all subscription plans.
     *
     * This method processes the query to find all subscription plans in the system.
     * It's used for displaying all available options to users.
     *
     * @param query The query for all subscriptions
     * @return A List of all Subscription objects
     */
    List<Subscription> handle(GetAllSubscriptionsQuery query);

    /**
     * Handles the query to retrieve all subscriptions with a specific type.
     *
     * This method processes the query to find subscriptions filtered by their type (BASIC/PREMIUM).
     * It's useful for displaying plans by type or filtering subscription options.
     *
     * @param query The query containing the subscription type
     * @return A List of Subscription objects with the specified type
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Subscription> handle(GetAllSubscriptionsByTypeQuery query);

    /**
     * Handles the query to retrieve recommended subscriptions.
     *
     * This method processes the query to find all recommended subscription plans.
     * Used for displaying featured or recommended plans to users.
     *
     * @param query The query for recommended subscriptions
     * @return A List of recommended Subscription objects
     */
    List<Subscription> handle(GetRecommendedSubscriptionsQuery query);

    /**
     * Retrieves all subscription plans in the system.
     *
     * @deprecated Use handle(GetAllSubscriptionsQuery) instead
     * @return A List of all Subscription objects in the system
     */
    @Deprecated
    List<Subscription> getAllSubscriptions();

    /**
     * Retrieves subscription plans by type.
     *
     * @deprecated Use handle(GetAllSubscriptionsByTypeQuery) instead
     * @param type The subscription type to filter by
     * @return A List of Subscription objects with the specified type
     * @throws IllegalArgumentException if type is null
     */
    @Deprecated
    List<Subscription> getSubscriptionsByType(Subscription.SubscriptionType type);

    /**
     * Retrieves all recommended subscription plans.
     *
     * @deprecated Use handle(GetRecommendedSubscriptionsQuery) instead
     * @return A List of recommended Subscription objects
     */
    @Deprecated
    List<Subscription> getRecommendedSubscriptions();

    /**
     * Counts the total number of subscription plans.
     *
     * This method provides a quick count of all available subscription plans,
     * useful for analytics and administrative reporting.
     *
     * @return The total count of subscription plans
     */
    long getSubscriptionCount();
}
