package com.geopslabs.geops.backend.payments.application.internal.queryservices;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByStatusQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetPaymentByIdQuery;
import com.geopslabs.geops.backend.payments.domain.services.PaymentQueryService;
import com.geopslabs.geops.backend.payments.infrastructure.persistence.jpa.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * PaymentQueryServiceImpl
 *
 * Implementation of the PaymentQueryService that handles all query operations
 * for payment transactions. This service implements the business logic for
 * retrieving and searching payments following DDD principles.
 *
 * @summary Implementation of payment query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    /**
     * Constructor for dependency injection
     *
     * @param paymentRepository The repository for payment data access
     */
    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Payment> handle(GetPaymentByIdQuery query) {
        try {
            return paymentRepository.findById(query.paymentId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving payment by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Payment> handle(GetAllPaymentsByUserIdQuery query) {
        try {
            return paymentRepository.findByUser_Id(query.userId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving payments by user ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Payment> handle(GetAllPaymentsByStatusQuery query) {
        try {
            return paymentRepository.findByStatus(query.status());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving payments by status: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Payment> getAllPayments() {
        try {
            return paymentRepository.findAll();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving all payments: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getPaymentCountByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        try {
            return paymentRepository.countByUser_Id(userId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error counting payments by user ID: " + e.getMessage());
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Payment> getPaymentsByCartId(Long cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("cartId cannot be null");
        }

        try {
            return paymentRepository.findByCart_Id(cartId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving payments by cart ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsPaymentByCartId(Long cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("cartId cannot be null");
        }

        try {
            return paymentRepository.existsByCart_Id(cartId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error checking payment existence by cart ID: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves completed payments for a specific user.
     *
     * This method provides a way to get only successful transactions for a user,
     * which can be useful for purchase history and analytics.
     *
     * @param userId The unique identifier of the user
     * @return A List of completed Payment objects for the specified user
     * @throws IllegalArgumentException if userId is null or empty
     */
    public List<Payment> getCompletedPaymentsByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        try {
            return paymentRepository.findCompletedPaymentsByUserId(userId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving completed payments by user ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves all pending payments in the system.
     *
     * This method is useful for monitoring and processing pending transactions.
     *
     * @return A List of pending Payment objects
     */
    public List<Payment> getPendingPayments() {
        try {
            return paymentRepository.findPendingPayments();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving pending payments: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves all failed payments in the system.
     *
     * This method is useful for analyzing failed transactions and troubleshooting.
     *
     * @return A List of failed Payment objects
     */
    public List<Payment> getFailedPayments() {
        try {
            return paymentRepository.findFailedPayments();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving failed payments: " + e.getMessage());
            return List.of();
        }
    }
}
