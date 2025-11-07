package com.geopslabs.geops.backend.payments.application.internal.commandservices;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.domain.model.commands.CreatePaymentCommand;
import com.geopslabs.geops.backend.payments.domain.model.commands.UpdatePaymentStatusCommand;
import com.geopslabs.geops.backend.payments.domain.services.PaymentCommandService;
import com.geopslabs.geops.backend.payments.infrastructure.persistence.jpa.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * PaymentCommandServiceImpl
 *
 * Implementation of the PaymentCommandService that handles all command operations
 * for payment transactions. This service implements the business logic for
 * creating, updating, and managing payments following DDD principles.
 *
 * @summary Implementation of payment command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    /**
     * Constructor for dependency injection
     *
     * @param paymentRepository The repository for payment data access
     */
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> handle(CreatePaymentCommand command) {
        try {
            // Create new payment from command
            var payment = new Payment(command);

            // Save the payment to the repository
            var savedPayment = paymentRepository.save(payment);

            return Optional.of(savedPayment);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error creating payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> handle(UpdatePaymentStatusCommand command) {
        try {
            // Find the existing payment by ID
            var existingPaymentOpt = paymentRepository.findById(command.paymentId());

            if (existingPaymentOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingPayment = existingPaymentOpt.get();

            // Update payment status based on command
            switch (command.status()) {
                case COMPLETED -> existingPayment.completePayment(command.completedAt());
                case FAILED -> existingPayment.failPayment();
                // PENDING status doesn't require special handling as it's the default
            }

            // Save the updated payment
            var updatedPayment = paymentRepository.save(existingPayment);

            return Optional.of(updatedPayment);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error updating payment status: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> completePayment(Long paymentId, String completedAt) {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }

        if (completedAt == null || completedAt.isBlank()) {
            completedAt = LocalDateTime.now().toString();
        }

        try {
            // Find the existing payment by ID
            var existingPaymentOpt = paymentRepository.findById(paymentId);

            if (existingPaymentOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingPayment = existingPaymentOpt.get();

            // Complete the payment
            existingPayment.completePayment(completedAt);

            // Save the completed payment
            var completedPayment = paymentRepository.save(existingPayment);

            return Optional.of(completedPayment);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error completing payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> failPayment(Long paymentId) {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }

        try {
            // Find the existing payment by ID
            var existingPaymentOpt = paymentRepository.findById(paymentId);

            if (existingPaymentOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingPayment = existingPaymentOpt.get();

            // Mark payment as failed
            existingPayment.failPayment();

            // Save the failed payment
            var failedPayment = paymentRepository.save(existingPayment);

            return Optional.of(failedPayment);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error failing payment: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> updatePaymentCode(Long paymentId, String paymentCode) {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }

        if (paymentCode == null || paymentCode.isBlank()) {
            throw new IllegalArgumentException("paymentCode cannot be null or empty");
        }

        try {
            // Find the existing payment by ID
            var existingPaymentOpt = paymentRepository.findById(paymentId);

            if (existingPaymentOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingPayment = existingPaymentOpt.get();

            // Update payment code
            existingPayment.updatePaymentCode(paymentCode);

            // Save the updated payment
            var updatedPayment = paymentRepository.save(existingPayment);

            return Optional.of(updatedPayment);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error updating payment code: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deletePayment(Long paymentId) {
        if (paymentId == null || paymentId <= 0) {
            throw new IllegalArgumentException("paymentId cannot be null or negative");
        }

        try {
            // First check if payment exists
            var existingPaymentOpt = paymentRepository.findById(paymentId);

            if (existingPaymentOpt.isEmpty()) {
                return false;
            }

            // Delete the payment
            paymentRepository.deleteById(paymentId);

            return true;

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }
}
