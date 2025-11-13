package com.geopslabs.geops.backend.payments.domain.services;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByStatusQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetPaymentByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * PaymentQueryService
 *
 * Domain service interface that defines query operations for payment transactions.
 * This service handles all read operations following the Command Query Responsibility
 * Segregation (CQRS) pattern, providing various ways to retrieve payment data.
 *
 * @summary Service interface for handling payment query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface PaymentQueryService {

    /**
     * Handles the query to retrieve a payment by its unique identifier.
     *
     * This method processes the query to find a specific payment using its ID.
     * It's commonly used for payment verification, status checking, and detailed views.
     *
     * @param query The query containing the payment ID
     * @return An Optional containing the Payment if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Payment> handle(GetPaymentByIdQuery query);

    /**
     * Handles the query to retrieve all payments for a specific user.
     *
     * This method processes the query to find all payments belonging to a user.
     * It's useful for displaying user payment history and purchase tracking.
     *
     * @param query The query containing the user ID
     * @return A List of Payment objects for the specified user
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Payment> handle(GetAllPaymentsByUserIdQuery query);

    /**
     * Handles the query to retrieve all payments with a specific status.
     *
     * This method processes the query to find payments filtered by their status.
     * It's primarily used for administrative purposes and payment monitoring.
     *
     * @param query The query containing the payment status
     * @return A List of Payment objects with the specified status
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Payment> handle(GetAllPaymentsByStatusQuery query);

    /**
     * Retrieves all payment transactions in the system.
     *
     * This method provides a comprehensive view of all payments,
     * useful for administrative dashboards and reporting purposes.
     *
     * @return A List of all Payment objects in the system
     */
    List<Payment> getAllPayments();

    /**
     * Retrieves the count of payments for a specific user.
     *
     * This method provides a quick way to determine how many payments
     * a user has made, which can be useful for analytics and user insights.
     *
     * @param userId The unique identifier of the user
     * @return The number of payments made by the specified user
     * @throws IllegalArgumentException if userId is null or empty
     */
    long getPaymentCountByUserId(String userId);

    /**
     * Retrieves payments by cart ID.
     *
     * This method finds payments associated with a specific cart,
     * useful for tracking cart-to-payment conversions and cart history.
     *
     * @param cartId The unique identifier of the cart
     * @return A List of Payment objects associated with the specified cart
     * @throws IllegalArgumentException if cartId is null or empty
     */
    List<Payment> getPaymentsByCartId(String cartId);

    /**
     * Checks if a payment exists for a specific cart.
     *
     * This method provides a quick check to determine if a cart
     * has been successfully paid for, without retrieving the full payment object.
     *
     * @param cartId The unique identifier of the cart
     * @return true if a payment exists for the cart, false otherwise
     * @throws IllegalArgumentException if cartId is null or empty
     */
    boolean existsPaymentByCartId(String cartId);
}
