package com.geopslabs.geops.backend.favorites.domain.model.queries;

/**
 * GetFavoriteByIdQuery
 *
 * Query record to retrieve a favorite by its ID.
 * Useful for validation before deletion.
 *
 * @summary Query to retrieve a favorite by ID
 * @param id The favorite unique identifier
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetFavoriteByIdQuery(Long id) {
    public GetFavoriteByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
}

