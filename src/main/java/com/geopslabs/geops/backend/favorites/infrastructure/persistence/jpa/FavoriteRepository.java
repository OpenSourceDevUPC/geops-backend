package com.geopslabs.geops.backend.favorites.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FavoriteRepository
 *
 * JPA Repository interface for Favorite aggregate root
 * This repository provides data access operations for favorites
 *
 * @summary JPA Repository for favorite data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Finds all favorites for a specific user
     *
     * @param userId The unique identifier of the user
     * @return A List of Favorite objects for the specified user
     */
    List<Favorite> findByUser_Id(Long userId);

    /**
     * Finds a favorite by user ID and offer ID
     *
     * @param userId The unique identifier of the user
     * @param offerId The unique identifier of the offer
     * @return An Optional containing the Favorite if found, empty otherwise
     */
    Optional<Favorite> findByUser_IdAndOffer_Id(Long userId, Long offerId);

    /**
     * Checks if a favorite exists for a specific user and offer
     *
     * @param userId The unique identifier of the user
     * @param offerId The unique identifier of the offer
     * @return true if the favorite exists, false otherwise
     */
    boolean existsByUser_IdAndOffer_Id(Long userId, Long offerId);

    /**
     * Counts the total number of favorites for a specific offer
     *
     * @param offerId The unique identifier of the offer
     * @return The number of users who favorites this offer
     */
    long countByOffer_Id(Long offerId);

    /**
     * Deletes all favorites for a specific offer
     * Useful for cascade deletion when an offer is removed
     *
     * @param offerId The unique identifier of the offer
     * @return The number of deleted favorites
     */
    long deleteByOffer_Id(Long offerId);

    /**
     * Deletes a favorite by user ID and offer ID
     * Useful for removing a specific favorite relationship
     *
     * @param userId The unique identifier of the user
     * @param offerId The unique identifier of the offer
     * @return The number of deleted favorites (0 or 1)
     */
    long deleteByUser_IdAndOffer_Id(Long userId, Long offerId);
}

