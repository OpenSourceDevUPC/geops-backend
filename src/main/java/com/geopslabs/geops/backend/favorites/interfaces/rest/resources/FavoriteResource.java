package com.geopslabs.geops.backend.favorites.interfaces.rest.resources;

/**
 * FavoriteResource
 *
 * Resource DTO for representing favorite data via REST API
 * This resource encapsulates the favorite information returned in API responses
 *
 * @summary Resource for favorite representation
 * @param id The unique identifier of the favorite
 * @param userId The ID of the user who created the favorite
 * @param offerId The ID of the favorite offer
 * @param createdAt The timestamp when the favorite was created
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record FavoriteResource(
        Long id,
        Long userId,
        Long offerId,
        String createdAt
) {
}
