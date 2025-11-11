package com.geopslabs.geops.backend.favorites.domain.services;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoriteByIdQuery;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoriteByUserIdAndOfferIdQuery;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoritesByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * FavoriteQueryService
 *
 * Domain service interface that defines query operations for favorites.
 * This service handles all read operations following the Command Query Responsibility
 * Segregation (CQRS) pattern, providing various ways to retrieve favorite data
 *
 * @summary Service interface for handling favorite query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface FavoriteQueryService {

    /**
     * Handles the query to retrieve all favorites for a specific user
     *
     * This method processes the query to find all favorites associated with a given userId
     * Used to display the user's list of favorite offers
     * Endpoint: GET /api/v1/favorites?userId={id}
     *
     * @param query The query containing the userId
     * @return A list of Favorite objects associated with the user
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Favorite> handle(GetFavoritesByUserIdQuery query);

    /**
     * Handles the query to retrieve a favorite by user ID and offer ID
     *
     * This method processes the query to find a specific favorite based on the
     * combination of userId and offerId. Useful for checking if an offer is already
     * favorite by the user
     *
     * @param query The query containing the userId and offerId
     * @return An Optional containing the Favorite if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Favorite> handle(GetFavoriteByUserIdAndOfferIdQuery query);

    /**
     * Handles the query to retrieve a favorite by its unique identifier
     *
     * This method processes the query to find a favorite based on its ID
     * Useful for operations that require direct access to a favorite entity
     *
     * @param query The query containing the favorite ID
     * @return An Optional containing the Favorite if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Favorite> handle(GetFavoriteByIdQuery query);
}


