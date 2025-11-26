package com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DetailsConsumerRepository
 *
 * JPA Repository interface for DetailsConsumer aggregate root
 * This repository provides data access operations for consumer details
 *
 * @summary JPA Repository for consumer details data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface DetailsConsumerRepository extends JpaRepository<DetailsConsumer, Long> {

    /**
     * Finds consumer details by user ID
     *
     * @param userId The user ID to search for
     * @return An Optional containing the consumer details if found, empty otherwise
     */
    Optional<DetailsConsumer> findByUserId(Long userId);

    /**
     * Checks if consumer details exist for a given user
     *
     * @param userId The user ID to check
     * @return true if consumer details exist, false otherwise
     */
    boolean existsByUserId(Long userId);
}

