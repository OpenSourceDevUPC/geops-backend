package com.geopslabs.geops.backend.subscriptions.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SubscriptionRepository
 *
 * JPA Repository interface for Subscription aggregate root.
 * This repository provides data access operations for subscription plans,
 * matching the simple subscription structure: { id, price, recommended, type }
 *
 * @summary JPA Repository for subscription plan data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Finds all subscriptions with a specific type.
     *
     * @param type The subscription type to filter by (BASIC or PREMIUM)
     * @return A List of Subscription objects with the specified type
     */
    List<Subscription> findByType(SubscriptionType type);

    /**
     * Finds all recommended subscription plans.
     *
     * @return A List of recommended Subscription objects
     */
    @Query("SELECT s FROM Subscription s WHERE s.recommended = true")
    List<Subscription> findRecommendedSubscriptions();

    /**
     * Finds subscriptions by recommendation status.
     *
     * @param recommended Whether to find recommended or non-recommended plans
     * @return A List of Subscription objects with the specified recommendation status
     */
    List<Subscription> findByRecommended(Boolean recommended);
}
