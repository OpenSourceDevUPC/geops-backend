package com.geopslabs.geops.backend.cart.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * CartItem entity
 * Represents a single item inside a user's cart. Stored as a separate entity
 * to allow efficient updates and queries.
 */
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "user_id", nullable = false)
    @Getter
    private String userId;

    @Column(name = "offer_id", nullable = false)
    @Getter
    private String offerId;

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

    public CartItem(String userId, String offerId, String offerTitle, Double offerPrice, String offerImageUrl, Integer quantity) {
        this.userId = userId;
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerPrice = offerPrice;
        this.offerImageUrl = offerImageUrl;
        this.quantity = quantity == null ? 1 : quantity;
        this.total = this.quantity * (this.offerPrice == null ? 0.0 : this.offerPrice);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.total = this.quantity * (this.offerPrice == null ? 0.0 : this.offerPrice);
    }

}
