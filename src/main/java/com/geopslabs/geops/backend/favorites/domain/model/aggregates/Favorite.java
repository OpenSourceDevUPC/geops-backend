package com.geopslabs.geops.backend.favorites.domain.model.aggregates;

import com.geopslabs.geops.backend.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * Favorite Aggregate Root
 *
 * This aggregate represents a user's favorite offer in the GeOps platform.
 * It manages the many-to-many relationship between users and offers,
 * allowing users to mark offers as favorites for quick access
 *
 * @summary Manages user favorite offers (heart button functionality)
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "favorites")
@Getter
public class Favorite extends AuditableAbstractAggregateRoot<Favorite> {

    /**
     * User identifier who favorites the offer
     */
    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * Offer identifier that was favorites
     */
    @Column(name = "offer_id", nullable = false)
    private String offerId;

    /**
     * Default constructor for JPA
     */
    protected Favorite() {}

    /**
     * Creates a new Favorite from a CreateFavoriteCommand
     *
     * @param command The command containing favorite creation data
     */
    public Favorite(CreateFavoriteCommand command) {
        this.userId = command.userId();
        this.offerId = command.offerId();
    }

    /**
     * Checks if this favorite belongs to a specific user
     *
     * @param userId The user ID to check.
     * @return true if the favorite belongs to the user, false otherwise
     */
    public boolean belongsToUser(String userId) {
        return this.userId != null && this.userId.equals(userId);
    }

    /**
     * Checks if this favorite is for a specific offer
     *
     * @param offerId The offer ID to check
     * @return true if the favorite is for the offer, false otherwise
     */
    public boolean isForOffer(String offerId) {
        return this.offerId != null && this.offerId.equals(offerId);
    }
}
