package com.geopslabs.geops.backend.offers.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OfferRepository
 *
 * JPA Repository interface for Offer aggregate root
 * This repository provides data access operations for offers
 * including custom queries for offer management and retrieval operations
 *
 * @summary JPA Repository for offer data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    /**
     * Finds all offers by a list of IDs
     *
     * @param ids The list of offer IDs to retrieve
     * @return A List of Offer objects with the specified IDs
     */
    List<Offer> findByIdIn(List<Long> ids);
}
