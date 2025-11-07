package com.geopslabs.geops.backend.payments.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PaymentRepository
 *
 * JPA Repository interface for Payment aggregate root.
 * This repository provides data access operations for payment transactions,
 * including custom queries for payment management and reporting operations.
 *
 * @summary JPA Repository for payment data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds all payments for a specific user.
     *
     * @param userId The unique identifier of the user
     * @return A List of Payment objects for the specified user
     */
    List<Payment> findByUserId(String userId);

    /**
     * Finds all payments with a specific status.
     *
     * @param status The payment status to filter by
     * @return A List of Payment objects with the specified status
     */
    List<Payment> findByStatus(PaymentStatus status);

    /**
     * Finds all payments associated with a specific cart.
     *
     * @param cartId The unique identifier of the cart
     * @return A List of Payment objects associated with the specified cart
     */
    List<Payment> findByCartId(String cartId);

    /**
     * Finds a payment by its payment code.
     *
     * @param paymentCode The payment reference code
     * @return An Optional containing the Payment if found, empty otherwise
     */
    Optional<Payment> findByPaymentCode(String paymentCode);

    /**
     * Checks if a payment exists for a specific cart.
     *
     * @param cartId The unique identifier of the cart
     * @return true if a payment exists for the cart, false otherwise
     */
    boolean existsByCartId(String cartId);

    /**
     * Counts the number of payments for a specific user.
     *
     * @param userId The unique identifier of the user
     * @return The count of payments for the specified user
     */
    long countByUserId(String userId);

    /**
     * Finds all completed payments for a specific user.
     *
     * @param userId The unique identifier of the user
     * @return A List of completed Payment objects for the specified user
     */
    @Query("SELECT p FROM Payment p WHERE p.userId = :userId AND p.status = 'COMPLETED'")
    List<Payment> findCompletedPaymentsByUserId(@Param("userId") String userId);

    /**
     * Finds all pending payments.
     *
     * This query is useful for monitoring pending transactions and
     * identifying payments that may need attention.
     *
     * @return A List of Payment objects with PENDING status
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING'")
    List<Payment> findPendingPayments();

    /**
     * Finds all failed payments.
     *
     * This query is useful for analyzing failed transactions and
     * understanding payment processing issues.
     *
     * @return A List of Payment objects with FAILED status
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'FAILED'")
    List<Payment> findFailedPayments();

    /**
     * Finds payments by customer email.
     *
     * This query can be useful for customer support and
     * tracking payments by customer contact information.
     *
     * @param customerEmail The customer's email address
     * @return A List of Payment objects for the specified email
     */
    List<Payment> findByCustomerEmail(String customerEmail);

    /**
     * Finds payments by product type.
     *
     * This query helps in analyzing payments by product category
     * and understanding product-specific payment patterns.
     *
     * @param productType The type of product being purchased
     * @return A List of Payment objects for the specified product type
     */
    List<Payment> findByProductType(String productType);

    /**
     * Finds the most recent payment for a user.
     *
     * @param userId The unique identifier of the user
     * @return An Optional containing the most recent Payment for the user
     */
    @Query("SELECT p FROM Payment p WHERE p.userId = :userId ORDER BY p.createdAt DESC LIMIT 1")
    Optional<Payment> findMostRecentPaymentByUserId(@Param("userId") String userId);

    /**
     * Counts payments by payment method.
     *
     * This query is useful for analyzing payment method preferences
     * and usage statistics across the platform.
     *
     * @param paymentMethod The payment method to count
     * @return The count of payments using the specified method
     */
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentMethod = :paymentMethod")
    long countByPaymentMethod(@Param("paymentMethod") Payment.PaymentMethod paymentMethod);
}
