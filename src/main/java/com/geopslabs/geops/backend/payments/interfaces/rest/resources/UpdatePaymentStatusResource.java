package com.geopslabs.geops.backend.payments.interfaces.rest.resources;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;

/**
 * UpdatePaymentStatusResource
 *
 * Resource Resource for updating payment status via REST API.
 * This resource represents the request payload for updating the status
 * of an existing payment transaction.
 *
 * @summary Request resource for updating payment status
 * @param status The new payment status
 * @param completedAt Timestamp when payment was completed (optional, only for COMPLETED status)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdatePaymentStatusResource(
    PaymentStatus status,
    String completedAt
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdatePaymentStatusResource {
        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }

        // If status is COMPLETED, completedAt should be provided
        if (status == PaymentStatus.COMPLETED && (completedAt == null || completedAt.isBlank())) {
            throw new IllegalArgumentException("completedAt is required when status is COMPLETED");
        }
    }
}
