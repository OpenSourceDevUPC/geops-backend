package com.geopslabs.geops.backend.cart.domain.model.aggregates;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * CartItem entity
 * Represents a single item inside a user's cart. Stored as a separate entity
 * to allow efficient updates and queries.
 */
@Entity
@Table(name = "cart_items", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_offer_id", columnList = "offer_id"),
    @Index(name = "idx_cart_id", columnList = "cart_id")
})
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    @Getter
    private Offer offer;

    @Column(name = "offer_title")
    @Getter
    private String offerTitle;

    @Column(name = "offer_price")
    @Getter
    private Double offerPrice;

    @Column(name = "offer_image_url")
    @Getter
    private String offerImageUrl;

    @Column(name = "quantity", nullable = false)
    @Getter
    private Integer quantity;

    @Column(name = "total", nullable = false)
    @Getter
    private Double total;

    protected CartItem() {
        // for JPA
    }

    public CartItem(User user, Offer offer, String offerTitle, Double offerPrice, String offerImageUrl, Integer quantity) {
        this.user = user;
        this.offer = offer;
        this.offerTitle = offerTitle;
        this.offerPrice = offerPrice;
        this.offerImageUrl = offerImageUrl;
        this.quantity = quantity == null ? 1 : quantity;
        this.total = this.quantity * (this.offerPrice == null ? 0.0 : this.offerPrice);
    }

    /**
     * Gets the user ID for this cart item
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    /**
     * Gets the offer ID for this cart item
     *
     * @return The offer ID
     */
    public Long getOfferId() {
        return this.offer != null ? this.offer.getId() : null;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.total = this.quantity * (this.offerPrice == null ? 0.0 : this.offerPrice);
    }

}
