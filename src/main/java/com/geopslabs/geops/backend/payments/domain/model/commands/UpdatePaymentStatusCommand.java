package com.geopslabs.geops.backend.payments.domain.model.commands;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;

/**
 * UpdatePaymentStatusCommand
 *
 * Command record for updating the status of an existing payment transaction.
 * This command allows updating payment status and completion timestamp,
 * commonly used to mark payments as completed or failed.
 *
 * @summary Command to update payment transaction status
 * @param paymentId The unique identifier of the payment to update
 * @param status The new payment status
 * @param completedAt Timestamp when payment was completed (optional, only for COMPLETED status)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdatePaymentStatusCommand(
    Long paymentId,
    PaymentStatus status,
    String completedAt
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdatePaymentStatusCommand {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }

        if (status == null) {
            throw new IllegalArgumentException("status cannot be null");
        }

        // If status is COMPLETED, completedAt should be provided
        if (status == PaymentStatus.COMPLETED && (completedAt == null || completedAt.isBlank())) {
            throw new IllegalArgumentException("completedAt is required when status is COMPLETED");
        }
    }
}
