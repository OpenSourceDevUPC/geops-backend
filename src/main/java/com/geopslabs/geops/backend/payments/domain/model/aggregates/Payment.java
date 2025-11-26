package com.geopslabs.geops.backend.payments.domain.model.aggregates;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.payments.domain.model.commands.CreatePaymentCommand;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Payment Aggregate Root
 *
 * This aggregate represents a payment transaction in the system.
 * It manages the complete payment lifecycle including user information,
 * cart details, amount, payment method, and transaction status.
 *
 * @summary Manages payment transactions and their associated data
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_cart_id", columnList = "cart_id"),
    @Index(name = "idx_status", columnList = "status")
})
public class Payment extends AuditableAbstractAggregateRoot<Payment> {

    /**
     * Reference to the user who initiated the payment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    private User user;

    /**
     * Reference to the cart associated with this payment
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @Getter
    private Cart cart;

    /**
     * Payment amount in the system's base currency
     */
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @Getter
    private BigDecimal amount;

    /**
     * Type of product being purchased (e.g., 'offer', 'subscription')
     */
    @Column(name = "product_type")
    @Getter
    private String productType;

    /**
     * Reference to the offer being purchased
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "offer_id", nullable = true)
    @Getter
    private Offer offer;

    /**
     * Generated payment codes for purchased items stored as JSON
     */
    @Column(name = "payment_codes", columnDefinition = "JSON")
    @Getter
    private String paymentCodes;

    /**
     * Payment method used for this transaction
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    @Getter
    private PaymentMethod paymentMethod;

    /**
     * Current status of the payment transaction
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Getter
    private PaymentStatus status;

    /**
     * Customer's email address
     */
    @Column(name = "customer_email", nullable = false)
    @Getter
    private String customerEmail;

    /**
     * Customer's first name
     */
    @Column(name = "customer_first_name", nullable = false)
    @Getter
    private String customerFirstName;

    /**
     * Customer's last name
     */
    @Column(name = "customer_last_name", nullable = false)
    @Getter
    private String customerLastName;

    /**
     * Payment reference code for tracking (Yape, card transaction reference, etc.)
     */
    @Column(name = "payment_code")
    @Getter
    private String paymentCode;

    /**
     * Timestamp when the payment was completed (nullable for pending payments)
     */
    @Column(name = "completed_at")
    @Getter
    private String completedAt;

    /**
     * Default constructor for JPA
     */
    protected Payment() {}

    /**
     * Creates a new Payment from a CreatePaymentCommand
     *
     * @param command The command containing payment creation data
     * @param user The user entity reference
     * @param cart The cart entity reference
     * @param offer The offer entity reference (optional)
     */
    public Payment(CreatePaymentCommand command, User user, Cart cart, Offer offer) {
        this.user = user;
        this.cart = cart;
        this.amount = command.amount();
        this.productType = command.productType();
        this.paymentCodes = command.paymentCodes();
        this.paymentMethod = command.paymentMethod();
        this.status = PaymentStatus.PENDING; // Default status
        this.customerEmail = command.customerEmail();
        this.customerFirstName = command.customerFirstName();
        this.customerLastName = command.customerLastName();
        this.paymentCode = command.paymentCode();
    }

    /**
     * Gets the user ID for this payment
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    /**
     * Gets the cart ID for this payment
     *
     * @return The cart ID
     */
    public Long getCartId() {
        return this.cart != null ? this.cart.getId() : null;
    }

    /**
     * Gets the offer ID for this payment
     *
     * @return The offer ID
     */
    public Long getOfferId() {
        return this.offer != null ? this.offer.getId() : null;
    }

    /**
     * Completes the payment transaction
     *
     * @param completedAt Timestamp when payment was completed
     */
    public void completePayment(String completedAt) {
        this.status = PaymentStatus.COMPLETED;
        this.completedAt = completedAt;
    }

    /**
     * Marks the payment as failed
     */
    public void failPayment() {
        this.status = PaymentStatus.FAILED;
    }

    /**
     * Checks if the payment is completed
     *
     * @return true if payment status is COMPLETED, false otherwise
     */
    public boolean isCompleted() {
        return this.status == PaymentStatus.COMPLETED;
    }

    /**
     * Checks if the payment is pending
     *
     * @return true if payment status is PENDING, false otherwise
     */
    public boolean isPending() {
        return this.status == PaymentStatus.PENDING;
    }

    /**
     * Updates the payment code (for payment method references)
     *
     * @param paymentCode The new payment code
     */
    public void updatePaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    /**
     * Payment method enumeration
     */
    public enum PaymentMethod {
        CARD,
        YAPE,
        PLIN
    }

    /**
     * Payment status enumeration
     */
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}
