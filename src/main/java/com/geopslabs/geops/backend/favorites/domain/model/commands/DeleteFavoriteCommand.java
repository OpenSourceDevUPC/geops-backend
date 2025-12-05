package com.geopslabs.geops.backend.favorites.domain.model.commands;

/**
 * DeleteFavoriteCommand
 *
 * Command record that encapsulates the necessary data to delete a favorite entry
 * by user and offer identifiers. This command is used when a user wants to remove
 * a favorite offer (un-heart action)
 *
 * @summary Command to delete a favorite entry by userId and offerId
 * @param userId The unique identifier of the user
 * @param offerId The unique identifier of the offer
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteFavoriteCommand(
    Long userId,
    Long offerId
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public DeleteFavoriteCommand {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offerId cannot be null");
        }
    }
}

