package com.geopslabs.geops.backend.coupons.domain.model.aggregates;

import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.UpdateCouponCommand;
import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Coupon Aggregate Root
 *
 * This aggregate represents a coupon in the GeOps platform.
 * Coupons are generated from payments and can be redeemed for offers.
 * They have expiration dates and are tied to specific users and payments.
 *
 * @summary Manages coupon lifecycle and redemption
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "coupons", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_payment_id", columnList = "payment_id"),
    @Index(name = "idx_code", columnList = "code"),
    @Index(name = "idx_expires_at", columnList = "expires_at")
})
public class Coupon extends AuditableAbstractAggregateRoot<Coupon> {

    /**
     * Reference to the user who owns this coupon
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    private User user;

    /**
     * Reference to the payment that generated this coupon
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    @Getter
    private Payment payment;

    /**
     * Payment code generated at payment time
     */
    @Column(name = "payment_code", nullable = false)
    @Getter
    private String paymentCode;

    /**
     * Product type copied from payment (optional)
     */
    @Column(name = "product_type")
    @Getter
    private String productType;

    /**
     * Reference to the offer id (optional)
     */
    @Column(name = "offer_id")
    @Getter
    private Long offerId;

    /**
     * The coupon code to redeem
     */
    @Column(name = "code", nullable = false, unique = true)
    @Getter
    private String code;

    /**
     * Expiration date of the coupon (optional)
     */
    @Column(name = "expires_at")
    @Getter
    private String expiresAt;

    /**
     * Default constructor for JPA
     */
    protected Coupon() {}

    /**
     * Creates a new Coupon from a CreateCouponCommand
     *
     * @param command The command containing coupon creation data
     * @param user The user entity reference
     * @param payment The payment entity reference
     */
    public Coupon(CreateCouponCommand command, User user, Payment payment) {
        this.user = user;
        this.payment = payment;
        this.paymentCode = command.paymentCode();
        this.productType = command.productType();
        this.offerId = command.offerId();
        this.code = command.code();
        this.expiresAt = command.expiresAt();
    }

    /**
     * Gets the user ID for this coupon
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    /**
     * Gets the payment ID for this coupon
     *
     * @return The payment ID
     */
    public Long getPaymentId() {
        return this.payment != null ? this.payment.getId() : null;
    }

    /**
     * Updates the coupon with new information
     *
     * @param command The command containing updated coupon data
     */
    public void updateCoupon(UpdateCouponCommand command) {
        if (command.productType() != null) {
            this.productType = command.productType();
        }
        if (command.offerId() != null) {
            this.offerId = command.offerId();
        }
        if (command.code() != null) {
            this.code = command.code();
        }
        if (command.expiresAt() != null) {
            this.expiresAt = command.expiresAt();
        }
    }

    /**
     * Checks if the coupon has expired
     *
     * @return true if coupon has expired, false otherwise
     */
    public boolean isExpired() {
        if (this.expiresAt == null) {
            return false;
        }
        // Simple string comparison assuming ISO format
        return this.expiresAt.compareTo(java.time.Instant.now().toString()) < 0;
    }

    /**
     * Checks if the coupon is valid for redemption
     *
     * @return true if coupon is valid, false otherwise
     */
    public boolean isValid() {
        return !isExpired() && this.code != null && !this.code.isBlank();
    }
}
