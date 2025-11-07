package com.geopslabs.geops.backend.coupons.domain.model.aggregates;

import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.UpdateCouponCommand;
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
@Table(name = "coupons")
public class Coupon extends AuditableAbstractAggregateRoot<Coupon> {

    /**
     * User identifier who owns this coupon
     */
    @Column(name = "user_id", nullable = false)
    @Getter
    private String userId;

    /**
     * Payment identifier that generated this coupon
     */
    @Column(name = "payment_id", nullable = false)
    @Getter
    private String paymentId;

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
     */
    public Coupon(CreateCouponCommand command) {
        this.userId = command.userId();
        this.paymentId = command.paymentId();
        this.paymentCode = command.paymentCode();
        this.productType = command.productType();
        this.offerId = command.offerId();
        this.code = command.code();
        this.expiresAt = command.expiresAt();
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
