package com.geopslabs.geops.backend.favorites.interfaces.rest.resources;

/**
 * CreateFavoriteResource
 *
 * Resource representing the data required to create a new favorite offer for a user
 *
 * @summary Resource for creating a favorite offer
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateFavoriteResource(
    String userId,
    String offerId
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateFavoriteResource {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        if (offerId == null || offerId.isBlank()) {
            throw new IllegalArgumentException("offerId cannot be null or empty");
        }
    }
}

