package com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DetailsOwnerRepository
 *
 * JPA Repository interface for DetailsOwner aggregate root
 * This repository provides data access operations for owner details
 *
 * @summary JPA Repository for owner details data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface DetailsOwnerRepository extends JpaRepository<DetailsOwner, Long> {

    /**
     * Finds owner details by user ID
     *
     * @param userId The user ID to search for
     * @return An Optional containing the owner details if found, empty otherwise
     */
    Optional<DetailsOwner> findByUserId(Long userId);

    /**
     * Checks if owner details exist for a given user
     *
     * @param userId The user ID to check
     * @return true if owner details exist, false otherwise
     */
    boolean existsByUserId(Long userId);
}

