package com.geopslabs.geops.backend.subscriptions.domain.model.commands;

/**
 * DeleteSubscriptionCommand
 *
 * Command record that encapsulates the necessary data to delete a subscription plan.
 * This command validates input data to ensure the subscription ID is provided
 *
 * @summary Command to delete a subscription plan by ID
 * @param subscriptionId The unique identifier of the subscription plan
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteSubscriptionCommand(Long subscriptionId) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public DeleteSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscriptionId must be positive");
        }
    }
}

