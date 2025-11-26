package com.geopslabs.geops.backend.favorites.domain.model.commands;

/**
 * CreateFavoriteCommand
 *
 * Command record that encapsulates all the necessary data to create a new favorite entry.
 * This command validates input data and ensures that required fields are properly provided
 * for favorite creation
 *
 * @summary Command to create a new favorite entry
 * @param userId The unique identifier of the user creating the favorite
 * @param offerId The unique identifier of the offer being favorite
 *
 * @since 1.0
 * @author GeOps Labs
 */

public record CreateFavoriteCommand(
        Long userId,
        Long offerId
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateFavoriteCommand {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null or empty");
        }
    }
}
