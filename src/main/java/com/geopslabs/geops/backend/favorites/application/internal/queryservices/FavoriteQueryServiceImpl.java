package com.geopslabs.geops.backend.favorites.application.internal.queryservices;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoriteByIdQuery;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoriteByUserIdAndOfferIdQuery;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoritesByUserIdQuery;
import com.geopslabs.geops.backend.favorites.domain.services.FavoriteQueryService;
import com.geopslabs.geops.backend.favorites.infrastructure.persistence.jpa.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * FavoriteQueryServiceImpl
 *
 * Implementation of the FavoriteQueryService that handles all query operations
 * for favorites. This service implements the business logic for
 * retrieving and searching favorites following DDD principles.
 *
 * @summary Implementation of favorite query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class FavoriteQueryServiceImpl implements FavoriteQueryService {

    private final FavoriteRepository favoriteRepository;

    /**
     * Constructor for dependency injection
     *
     * @param favoriteRepository The repository for favorite data access
     */
    public FavoriteQueryServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Favorite> handle(GetFavoritesByUserIdQuery query) {
        try {
            return favoriteRepository.findByUserId(query.userId());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving favorites by user ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Favorite> handle(GetFavoriteByUserIdAndOfferIdQuery query) {
        try {
            return favoriteRepository.findByUserIdAndOfferId(
                query.userId(),
                query.offerId()
            );
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving favorite by userId and offerId: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Favorite> handle(GetFavoriteByIdQuery query) {
        try {
            return favoriteRepository.findById(query.id());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving favorite by ID: " + e.getMessage());
            return Optional.empty();
        }
    }
}


