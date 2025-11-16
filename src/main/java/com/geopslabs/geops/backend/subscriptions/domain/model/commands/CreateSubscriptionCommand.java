package com.geopslabs.geops.backend.subscriptions.domain.model.commands;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;

import java.math.BigDecimal;

/**
 * CreateSubscriptionCommand
 *
 * Command record that encapsulates all the necessary data to create a new subscription plan.
 * This command validates input data based on the frontend interface structure:
 * { id, price, recommended, type }
 *
 * @summary Command to create a new subscription plan
 * @param price The price of the subscription plan
 * @param recommended Whether this subscription plan is recommended
 * @param type The type of subscription plan (BASIC or PREMIUM)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateSubscriptionCommand(
    BigDecimal price,
    Boolean recommended,
    SubscriptionType type
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateSubscriptionCommand {
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

