package com.geopslabs.geops.backend.subscriptions.domain.model.commands;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

import java.math.BigDecimal;

/**
 * UpdateSubscriptionCommand
 *
 * Command record for updating an existing subscription plan.
 * This command allows partial updates of subscription data based on the
 * frontend interface structure: { id, price, recommended, type }
 *
 * @summary Command to update an existing subscription plan
 * @param subscriptionId The unique identifier of the subscription to update
 * @param price Updated price of the subscription plan (optional)
 * @param recommended Updated recommendation status (optional)
 * @param type Updated subscription type (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateSubscriptionCommand(
    Long subscriptionId,
    BigDecimal price,
    Boolean recommended,
    SubscriptionType type
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscriptionId cannot be null or negative");
        }

        // Validate optional fields if provided
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price cannot be negative if provided");
        }
    }
}
