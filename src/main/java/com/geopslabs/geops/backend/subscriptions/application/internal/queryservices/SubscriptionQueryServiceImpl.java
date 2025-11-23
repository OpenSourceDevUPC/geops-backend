package com.geopslabs.geops.backend.subscriptions.application.internal.queryservices;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetAllSubscriptionsByTypeQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetAllSubscriptionsQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetRecommendedSubscriptionsQuery;
import com.geopslabs.geops.backend.subscriptions.domain.services.SubscriptionQueryService;
import com.geopslabs.geops.backend.subscriptions.infrastructure.persistence.jpa.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SubscriptionQueryServiceImpl
 *
 * Implementation of the SubscriptionQueryService that handles all query operations
 * for subscription plans. This service implements the business logic for
 * retrieving and searching subscription plans following DDD principles.
 * Based on the simple subscription structure: { id, price, recommended, type }
 *
 * @summary Implementation of subscription plan query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    /**
     * Constructor for dependency injection
     *
     * @param subscriptionRepository The repository for subscription data access
     */
    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        try {
            return subscriptionRepository.findById(query.subscriptionId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving subscription by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> handle(GetAllSubscriptionsQuery query) {
        try {
            return subscriptionRepository.findAll();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving all subscriptions: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> handle(GetAllSubscriptionsByTypeQuery query) {
        try {
            return subscriptionRepository.findByType(query.type());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving subscriptions by type: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> handle(GetRecommendedSubscriptionsQuery query) {
        try {
            return subscriptionRepository.findRecommendedSubscriptions();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving recommended subscriptions: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> getAllSubscriptions() {
        try {
            return subscriptionRepository.findAll();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving all subscriptions: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> getSubscriptionsByType(Subscription.SubscriptionType type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }

        try {
            return subscriptionRepository.findByType(type);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving subscriptions by type: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> getRecommendedSubscriptions() {
        try {
            return subscriptionRepository.findRecommendedSubscriptions();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving recommended subscriptions: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSubscriptionCount() {
        try {
            return subscriptionRepository.count();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error counting subscriptions: " + e.getMessage());
            return 0;
        }
    }
}

