package com.geopslabs.geops.backend.subscriptions.application.internal.commandservices;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.UpdateSubscriptionCommand;
import com.geopslabs.geops.backend.subscriptions.domain.services.SubscriptionCommandService;
import com.geopslabs.geops.backend.subscriptions.infrastructure.persistence.jpa.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * SubscriptionCommandServiceImpl
 *
 * Implementation of the SubscriptionCommandService that handles all command operations
 * for subscription plans. This service implements the business logic for
 * creating, updating, and managing subscription plans following DDD principles.
 * Based on the simple subscription structure: { id, price, recommended, type }
 *
 * @summary Implementation of subscription plan command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    /**
     * Constructor for dependency injection
     *
     * @param subscriptionRepository The repository for subscription data access
     */
    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subscription> handle(CreateSubscriptionCommand command) {
        try {
            // Create new subscription plan from command
            var subscription = new Subscription(command);

            // Save the subscription to the repository
            var savedSubscription = subscriptionRepository.save(subscription);

            return Optional.of(savedSubscription);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error creating subscription plan: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subscription> handle(UpdateSubscriptionCommand command) {
        try {
            // Find the existing subscription by ID
            var existingSubscriptionOpt = subscriptionRepository.findById(command.subscriptionId());

            if (existingSubscriptionOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingSubscription = existingSubscriptionOpt.get();

            // Update the subscription with new data
            existingSubscription.updateSubscription(command);

            // Save the updated subscription
            var updatedSubscription = subscriptionRepository.save(existingSubscription);

            return Optional.of(updatedSubscription);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error updating subscription plan: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteSubscription(Long subscriptionId) {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscriptionId cannot be null or negative");
        }

        try {
            // First check if subscription exists
            var existingSubscriptionOpt = subscriptionRepository.findById(subscriptionId);

            if (existingSubscriptionOpt.isEmpty()) {
                return false;
            }

            // Delete the subscription
            subscriptionRepository.deleteById(subscriptionId);

            return true;

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error deleting subscription plan: " + e.getMessage());
            return false;
        }
    }
}

