package com.geopslabs.geops.backend.payments.interfaces.rest.resources;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentMethod;
import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;

import java.math.BigDecimal;

/**
 * PaymentResource
 *
 * Resource DTO for payment transaction responses via REST API.
 * This resource represents the response payload containing payment information
 * when retrieving payment data from the system.
 *
 * @summary Response resource for payment transaction data
 * @param id The unique identifier of the payment
 * @param userId The unique identifier of the user who made the payment
 * @param cartId The unique identifier of the cart that was purchased
 * @param amount The payment amount in the system's base currency
 * @param productType The type of product that was purchased
 * @param productId The ID of the purchased product
 * @param paymentCodes JSON string containing generated codes per purchased item
 * @param paymentMethod The payment method used for this transaction
 * @param status The current status of the payment transaction
 * @param customerEmail The customer's email address
 * @param customerFirstName The customer's first name
 * @param customerLastName The customer's last name
 * @param paymentCode The payment reference code for tracking
 * @param createdAt Timestamp when the payment was created
 * @param updatedAt Timestamp when the payment was last updated
 * @param completedAt Timestamp when the payment was completed (nullable)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record PaymentResource(
    Long id,
    String userId,
    String cartId,
    BigDecimal amount,
    String productType,
    String productId,
    String paymentCodes,
    PaymentMethod paymentMethod,
    PaymentStatus status,
    String customerEmail,
    String customerFirstName,
    String customerLastName,
    String paymentCode,
    String createdAt,
    String updatedAt,
    String completedAt
) {
    // This record doesn't need validation in the compact constructor
    // as it's used for response data that should already be validated
}
