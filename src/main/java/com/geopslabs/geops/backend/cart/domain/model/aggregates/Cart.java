package com.geopslabs.geops.backend.cart.domain.model.aggregates;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart aggregate root
 * Represents a user's shopping cart containing multiple CartItem entries.
 */
@Entity
@Table(name = "carts", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class Cart extends AuditableAbstractAggregateRoot<Cart> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Getter
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "total_items", nullable = false)
    @Getter
    private Integer totalItems = 0;

    @Column(name = "total_amount", nullable = false)
    @Getter
    private Double totalAmount = 0.0;

    protected Cart() {
        // JPA
    }

    public Cart(User user) {
        this.user = user;
    }

    /**
     * Gets the user ID for this cart
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    public void addItem(CartItem item) {
        item.setCart(this);
        this.items.add(item);
        recalculateTotals();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        recalculateTotals();
    }

    public void recalculateTotals() {
        this.totalItems = items.stream().mapToInt(i -> i.getQuantity() == null ? 0 : i.getQuantity()).sum();
        this.totalAmount = items.stream().mapToDouble(i -> i.getTotal() == null ? 0.0 : i.getTotal()).sum();
    }

}

