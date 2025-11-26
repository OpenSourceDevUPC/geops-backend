package com.geopslabs.geops.backend.subscriptions.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.UpdateSubscriptionCommand;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Subscription Aggregate Root
 *
 * This aggregate represents a subscription plan in the GeOps platform.
 * It manages subscription plan types, pricing, and recommendation status.
 * Based on the frontend interface: { id, price, recommended, type }
 *
 * @summary Manages subscription plans (BASIC/PREMIUM)
 * @since 1.0
 */
@Entity
@Table(name = "subscriptions")
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

    /**
     * Price of the subscription plan
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @Getter
    private BigDecimal price;

    /**
     * Whether this subscription plan is recommended
     *
     */
    @Column(name = "recommended", nullable = false, columnDefinition = "TINYINT(1)")
    @Getter
    private boolean recommended;

    /**
     * Type of subscription plan (BASIC or PREMIUM)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    @Getter
    private SubscriptionType type;

    /**
     * Default constructor for JPA
     */
    protected Subscription() {}

    /**
     * Creates a new Subscription from a CreateSubscriptionCommand
     *
     * @param command The command containing subscription creation data
     */
    public Subscription(CreateSubscriptionCommand command) {
        this.price = command.price();
        this.recommended = command.recommended();
        this.type = command.type();
    }

    /**
     * Updates the subscription with new information
     *
     * @param command The command containing updated subscription data
     */
    public void updateSubscription(UpdateSubscriptionCommand command) {
        if (command.price() != null) {
            this.price = command.price();
        }
        if (command.recommended() != null) {
            this.recommended = command.recommended();
        }
        if (command.type() != null) {
            this.type = command.type();
        }
    }

    /**
     * Checks if this subscription is a premium plan
     *
     * @return true if subscription type is PREMIUM, false otherwise
     */
    public boolean isPremium() {
        return this.type == SubscriptionType.PREMIUM;
    }

    /**
     * Checks if this subscription is a basic plan
     *
     * @return true if subscription type is BASIC, false otherwise
     */
    public boolean isBasic() {
        return this.type == SubscriptionType.BASIC;
    }

    /**
     * Subscription type enumeration
     */
    public enum SubscriptionType {
        BASIC,
        PREMIUM
    }
}
