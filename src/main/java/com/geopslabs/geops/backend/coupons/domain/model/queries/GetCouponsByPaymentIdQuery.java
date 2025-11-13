package com.geopslabs.geops.backend.coupons.domain.model.queries;

/**
 * GetCouponsByPaymentIdQuery
 *
 * Query record to retrieve all coupons generated from a specific payment.
 * This query is useful for tracking coupons generated from a particular payment
 * and understanding the relationship between payments and coupons.
 *
 * @summary Query to retrieve coupons by payment ID
 * @param paymentId The unique identifier of the payment that generated the coupons
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetCouponsByPaymentIdQuery(String paymentId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetCouponsByPaymentIdQuery {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("paymentId cannot be null or empty");
        }
    }
}
