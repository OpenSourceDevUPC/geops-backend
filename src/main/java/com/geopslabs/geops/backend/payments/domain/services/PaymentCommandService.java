package com.geopslabs.geops.backend.payments.domain.services;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.domain.model.commands.CreatePaymentCommand;
import com.geopslabs.geops.backend.payments.domain.model.commands.UpdatePaymentStatusCommand;

import java.util.Optional;

/**
 * PaymentCommandService
 *
 * Domain service interface that defines command operations for payment transactions.
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern.
 *
 * @summary Service interface for handling payment command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface PaymentCommandService {

    /**
     * Handles the creation of a new payment transaction.
     *
     * This method processes the command to create a new payment, validates the input,
     * and persists the payment data. It supports various payment methods and
     * handles customer information according to the frontend integration requirements.
     *
     * @param command The command containing all necessary data for payment creation
     * @return An Optional containing the created Payment if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Payment> handle(CreatePaymentCommand command);

    /**
     * Handles the update of payment status.
     *
     * This method processes the command to update a payment's status, such as
     * marking it as completed or failed. It also updates the completion timestamp
     * when applicable.
     *
     * @param command The command containing the payment ID and new status
     * @return An Optional containing the updated Payment if successful, empty if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Payment> handle(UpdatePaymentStatusCommand command);

    /**
     * Completes a payment transaction by its ID.
     *
     * This method marks a payment as completed and sets the completion timestamp.
     * It's a convenience method for payment completion operations.
     *
     * @param paymentId The unique identifier of the payment to complete
     * @param completedAt Timestamp when the payment was completed
     * @return An Optional containing the completed Payment if successful, empty if not found
     * @throws IllegalArgumentException if the payment ID is invalid
     */
    Optional<Payment> completePayment(Long paymentId, String completedAt);

    /**
     * Marks a payment transaction as failed by its ID.
     *
     * This method updates the payment status to FAILED, typically used when
     * payment processing encounters errors or is declined.
     *
     * @param paymentId The unique identifier of the payment to mark as failed
     * @return An Optional containing the failed Payment if successful, empty if not found
     * @throws IllegalArgumentException if the payment ID is invalid
     */
    Optional<Payment> failPayment(Long paymentId);

    /**
     * Updates the payment code for a transaction.
     *
     * This method updates the payment reference code, which can be useful for
     * storing transaction references from payment providers (Yape, card processors, etc.).
     *
     * @param paymentId The unique identifier of the payment to update
     * @param paymentCode The new payment reference code
     * @return An Optional containing the updated Payment if successful, empty if not found
     * @throws IllegalArgumentException if parameters are invalid
     */
    Optional<Payment> updatePaymentCode(Long paymentId, String paymentCode);

    /**
     * Deletes a payment transaction by its ID.
     *
     * This method removes a payment from the system. This operation should be used
     * with caution as it permanently removes payment data.
     *
     * @param paymentId The unique identifier of the payment to delete
     * @return true if the payment was successfully deleted, false if not found
     * @throws IllegalArgumentException if the payment ID is invalid
     */
    boolean deletePayment(Long paymentId);
}
