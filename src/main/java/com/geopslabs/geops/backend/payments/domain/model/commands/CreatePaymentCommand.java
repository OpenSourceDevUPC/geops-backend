package com.geopslabs.geops.backend.payments.domain.model.commands;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentMethod;

import java.math.BigDecimal;

/**
 * CreatePaymentCommand
 *
 * Command record that encapsulates all the necessary data to create a new payment transaction.
 * This command validates input data and ensures that required fields are properly provided
 * for payment creation, following the payment structure from the frontend integration.
 *
 * @summary Command to create a new payment transaction
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
public record CreatePaymentCommand(
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
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreatePaymentCommand {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        if (cartId == null) {
            throw new IllegalArgumentException("cartId cannot be null or empty");
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

        if (!isValidEmail(customerEmail)) {
            throw new IllegalArgumentException("customerEmail must be a valid email address");
        }

        if (customerFirstName == null || customerFirstName.isBlank()) {
            throw new IllegalArgumentException("customerFirstName cannot be null or empty");
        }

        if (customerLastName == null || customerLastName.isBlank()) {
            throw new IllegalArgumentException("customerLastName cannot be null or empty");
        }

        // Set default values for optional fields
        if (paymentCodes == null) {
            paymentCodes = "[]"; // Empty JSON array
        }
    }

    /**
     * Basic email validation
     *
     * @param email The email to validate
     * @return true if the email format is valid, false otherwise
     */
    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
}
