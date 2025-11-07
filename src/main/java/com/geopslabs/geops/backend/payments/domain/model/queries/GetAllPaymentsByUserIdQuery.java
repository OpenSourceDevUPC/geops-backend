package com.geopslabs.geops.backend.payments.domain.model.queries;

/**
 * GetAllPaymentsByUserIdQuery
 *
 * Query record to retrieve all payment transactions for a specific user.
 * This query helps in displaying user payment history, tracking user purchases,
 * and providing comprehensive payment information for a particular user.
 *
 * @summary Query to retrieve all payments for a specific user
 * @param userId The unique identifier of the user whose payments to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetAllPaymentsByUserIdQuery(String userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetAllPaymentsByUserIdQuery {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}
