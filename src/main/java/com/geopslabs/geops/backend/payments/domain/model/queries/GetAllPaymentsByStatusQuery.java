package com.geopslabs.geops.backend.payments.domain.model.queries;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;

/**
 * GetAllPaymentsByStatusQuery
 *
 * Query record to retrieve all payment transactions with a specific status.
 * This query is useful for administrative purposes, reporting, and monitoring
 * payment transactions based on their current status (PENDING, COMPLETED, FAILED).
 *
 * @summary Query to retrieve all payments with a specific status
 * @param status The payment status to filter by
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetAllPaymentsByStatusQuery(PaymentStatus status) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetAllPaymentsByStatusQuery {
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }
    }
}
