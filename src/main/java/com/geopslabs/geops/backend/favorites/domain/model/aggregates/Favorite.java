package com.geopslabs.geops.backend.favorites.domain.model.aggregates;

import com.geopslabs.geops.backend.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
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
@Table(name = "favorites",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_user_offer",
        columnNames = {"user_id", "offer_id"}
    ),
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_offer_id", columnList = "offer_id")
    }
)
@Getter
public class Favorite extends AuditableAbstractAggregateRoot<Favorite> {

    /**
     * Reference to the user who favorites the offer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Reference to the offer that was favorited
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    /**
     * Default constructor for JPA
     */
    protected Favorite() {}

    /**
     * Creates a new Favorite from a CreateFavoriteCommand
     *
     * @param command The command containing favorite creation data
     * @param user The user entity reference
     * @param offer The offer entity reference
     */
    public Favorite(CreateFavoriteCommand command, User user, Offer offer) {
        this.user = user;
        this.offer = offer;
    }

    /**
     * Gets the user ID for this favorite
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    /**
     * Gets the offer ID for this favorite
     *
     * @return The offer ID
     */
    public Long getOfferId() {
        return this.offer != null ? this.offer.getId() : null;
    }

    /**
     * Checks if this favorite belongs to a specific user
     *
     * @param userId The user ID to check.
     * @return true if the favorite belongs to the user, false otherwise
     */
    public boolean belongsToUser(Long userId) {
        return this.user != null && this.user.getId().equals(userId);
    }

    /**
     * Checks if this favorite is for a specific offer
     *
     * @param offerId The offer ID to check
     * @return true if the favorite is for the offer, false otherwise
     */
    public boolean isForOffer(Long offerId) {
        return this.offer != null && this.offer.getId().equals(offerId);
    }
}
