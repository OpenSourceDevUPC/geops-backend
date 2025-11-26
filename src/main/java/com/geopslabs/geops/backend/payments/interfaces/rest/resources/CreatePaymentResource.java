package com.geopslabs.geops.backend.payments.interfaces.rest.resources;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentMethod;

import java.math.BigDecimal;

/**
 * CreatePaymentResource
 *
 * Resource DTO for creating payment transactions via REST API.
 * This resource represents the request payload for payment creation,
 * containing all necessary information for processing a payment transaction.
 *
 * @summary Request resource for creating payment transactions
 * @param userId The unique identifier of the user making the payment
 * @param cartId The unique identifier of the cart being purchased
 * @param amount The payment amount in the system's base currency
 * @param productType The type of product being purchased (optional)
 * @param offerId The ID of the offer being purchased (optional)
 * @param paymentCodes JSON string containing generated codes per purchased item (optional)
 * @param paymentMethod The payment method used for this transaction
 * @param customerEmail The customer's email address
 * @param customerFirstName The customer's first name
 * @param customerLastName The customer's last name
 * @param paymentCode The payment reference code for tracking (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreatePaymentResource(
    Long userId,
    Long cartId,
    BigDecimal amount,
    String productType,
    Long offerId,
    String paymentCodes,
    PaymentMethod paymentMethod,
    String customerEmail,
    String customerFirstName,
    String customerLastName,
    String paymentCode
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreatePaymentResource {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (cartId == null) {
            throw new IllegalArgumentException("cartId cannot be null");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("paymentMethod cannot be null");
        }

        if (customerEmail == null || customerEmail.isBlank()) {
            throw new IllegalArgumentException("customerEmail cannot be null or empty");
        }

        if (customerFirstName == null || customerFirstName.isBlank()) {
            throw new IllegalArgumentException("customerFirstName cannot be null or empty");
        }

        if (customerLastName == null || customerLastName.isBlank()) {
            throw new IllegalArgumentException("customerLastName cannot be null or empty");
        }
    }
}
