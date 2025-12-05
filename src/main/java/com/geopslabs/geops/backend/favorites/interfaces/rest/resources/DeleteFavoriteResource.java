package com.geopslabs.geops.backend.favorites.interfaces.rest.resources;

/**
 * DeleteFavoriteResource
 *
 * Resource representing the data required to delete a favorite by user and offer
 *
 * @summary Resource for deleting a favorite by userId and offerId
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteFavoriteResource(
    Long userId,
    Long offerId
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public DeleteFavoriteResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }
    }
}

