package com.geopslabs.geops.backend.offers.domain.model.aggregates;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.Campaign;
import com.geopslabs.geops.backend.offers.domain.model.commands.CreateOfferCommand;
import com.geopslabs.geops.backend.offers.domain.model.commands.UpdateOfferCommand;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Offer Aggregate Root
 *
 * This aggregate represents a special offer or discount in the GeOps platform.
 * It manages offer details including partner information, pricing, validity,
 * location, category, and promotional codes
 *
 * @summary Manages special offers and discounts for users
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "offers")
@Getter
public class Offer extends AuditableAbstractAggregateRoot<Offer> {

    /**
     * Campaign id of the offer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;
    /**
     * Title of the offer
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Partner company providing the offer
     */
    @Column(name = "partner", nullable = false, length = 150)
    private String partner;

    /**
     * Price of the offer in the system's base currency
     */
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Code prefix used for generating promotional codes
     */
    @Column(name = "code_prefix", nullable = false, length = 10)
    private String codePrefix;

    /**
     * Expiration date of the offer
     */
    @Column(name = "valid_to", nullable = false)
    private LocalDate validTo;

    /**
     * Rating of the offer (0-5 scale)
     */
    @Column(name = "rating", nullable = false)
    private Integer rating;

    /**
     * Physical location where the offer is valid
     */
    @Column(name = "location", nullable = false, length = 255)
    private String location;

    /**
     * Category of the offer
     */
    @Column(name = "category", nullable = false, length = 100)
    private String category;

    /**
     * URL of the offer's image for display purposes
     */
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    /**
     * Default constructor for JPA
     */
    protected Offer() {
    }

    /**
     * Creates a new Offer from a CreateOfferCommand
     *
     * @param command The command containing offer creation data
     */
    public Offer(Campaign campaign, CreateOfferCommand command) {
        this.campaign = campaign;
        this.title = command.title();
        this.partner = command.partner();
        this.price = command.price();
        this.codePrefix = command.codePrefix();
        this.validTo = command.validTo();
        this.rating = command.rating();
        this.location = command.location();
        this.category = command.category();
        this.imageUrl = command.imageUrl();
    }

    /**
     * Updates the offer with new information
     *
     * @param command The command containing updated offer data
     */
    public void updateOffer(UpdateOfferCommand command) {
        if (command.title() != null) {
            this.title = command.title();
        }
        if (command.partner() != null) {
            this.partner = command.partner();
        }
        if (command.price() != null) {
            this.price = command.price();
        }
        if (command.codePrefix() != null) {
            this.codePrefix = command.codePrefix();
        }
        if (command.validTo() != null) {
            this.validTo = command.validTo();
        }
        if (command.rating() != null) {
            this.rating = command.rating();
        }
        if (command.location() != null) {
            this.location = command.location();
        }
        if (command.category() != null) {
            this.category = command.category();
        }
        if (command.imageUrl() != null) {
            this.imageUrl = command.imageUrl();
        }
    }

    /**
     * Checks if the offer has expired
     *
     * @return true if the offer's valid date has passed, false otherwise
     */
    public boolean isExpired() {
        return validTo != null && validTo.isBefore(LocalDate.now());
    }

}
