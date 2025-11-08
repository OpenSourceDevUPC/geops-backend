package com.geopslabs.geops.backend.favorites.domain.services;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.backend.favorites.domain.model.commands.CreateFavoriteCommand;

import java.util.Optional;

/**
 * FavoriteCommandService
 *
 * Domain service interface that defines command operations for managing favorites.
 * This service handles all write operations (Create, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern
 *
 * @summary Service interface for handling favorite command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface FavoriteCommandService {

    /**
     * Handles the creation of a new favorite.
     *
     * This method processes the command to create a new favorite,
     * validates the input, and persists the favorite data
     * Prevents duplicate favorites (same user + offer)
     *
     * @param command The command containing all necessary data for favorite creation
     * @return An Optional containing the created Favorite if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Favorite> handle(CreateFavoriteCommand command);

    /**
     * Handles the deletion of a favorite by its unique identifier
     *
     * This method processes the deletion of a favorite from the system
     *
     * @param id The unique identifier of the favorite to delete
     * @return true if the favorite was successfully deleted, false if not found
     * @throws IllegalArgumentException if the ID is invalid
     */
    boolean handleDelete(Long id);
}
