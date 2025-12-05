package com.geopslabs.geops.backend.subscriptions.domain.services;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.DeleteSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.UpdateSubscriptionCommand;

import java.util.Optional;

/**
 * SubscriptionCommandService
 *
 * Domain service interface that defines command operations for subscription plans.
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern.
 * Based on the simple subscription structure: { id, price, recommended, type }
 *
 * @summary Service interface for handling subscription plan command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface SubscriptionCommandService {

    /**
     * Handles the creation of a new subscription plan.
     *
     * This method processes the command to create a new subscription plan,
     * validates the input, and persists the subscription data.
     *
     * @param command The command containing all necessary data for subscription creation
     * @return An Optional containing the created Subscription if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Subscription> handle(CreateSubscriptionCommand command);

    /**
     * Handles the update of an existing subscription plan.
     *
     * This method processes the command to update subscription data such as price,
     * recommendation status, and type. It performs partial updates based on provided fields.
     *
     * @param command The command containing the subscription ID and updated data
     * @return An Optional containing the updated Subscription if successful, empty if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Subscription> handle(UpdateSubscriptionCommand command);

    /**
     * Handles the deletion of a subscription plan.
     *
     * This method processes the command to delete a subscription plan from the system.
     * This operation should be used with caution as it cannot be undone.
     *
     * @param command The command containing the subscription ID to delete
     * @return true if the subscription was successfully deleted, false if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    boolean handle(DeleteSubscriptionCommand command);

    /**
     * Deletes a subscription plan by its ID.
     *
     * @deprecated Use handle(DeleteSubscriptionCommand) instead
     * @param subscriptionId The unique identifier of the subscription to delete
     * @return true if the subscription was successfully deleted, false if not found
     * @throws IllegalArgumentException if the subscription ID is invalid
     */
    @Deprecated
    boolean deleteSubscription(Long subscriptionId);
}
