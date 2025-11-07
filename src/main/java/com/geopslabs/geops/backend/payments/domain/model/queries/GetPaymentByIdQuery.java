package com.geopslabs.geops.backend.payments.domain.model.queries;

/**
 * GetPaymentByIdQuery
 *
 * Query record to retrieve a payment transaction by its unique identifier.
 * This query is used to fetch specific payment details when needed
 * for validation, status checking, or displaying payment information.
 *
 * @summary Query to retrieve a payment by its ID
 * @param paymentId The unique identifier of the payment
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetPaymentByIdQuery(Long paymentId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetPaymentByIdQuery {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }
    }
}
