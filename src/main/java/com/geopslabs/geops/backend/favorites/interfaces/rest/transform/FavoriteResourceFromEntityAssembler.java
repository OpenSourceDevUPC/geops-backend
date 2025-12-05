package com.geopslabs.geops.backend.favorites.interfaces.rest.transform;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.backend.favorites.interfaces.rest.resources.FavoriteResource;

/**
 * FavoriteResourceFromEntityAssembler
 *
 * Assembler class responsible for converting Favorite entity objects
 * to FavoriteResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer Resources for API responses
 *
 * @summary Converts Favorite entity to FavoriteResource
 * @since 1.0
 * @author GeOps Labs
 */
public class FavoriteResourceFromEntityAssembler {

    /**
     * Converts a Favorite entity to a FavoriteResource
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses
     * It extracts all relevant favorite information for client consumption
     *
     * @param entity The Favorite entity from the domain layer
     * @return A FavoriteResource ready for REST API response
     */
    public static FavoriteResource toResourceFromEntity(Favorite entity) {
        return new FavoriteResource(
                entity.getId(),
                entity.getUserId(),
                entity.getOfferId(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null
        );
    }
}
