package com.geopslabs.geops.backend.coupons.domain.model.queries;

/**
 * GetCouponByCodeQuery
 *
 * Query record to retrieve a coupon by its redemption code.
 * This query is essential for coupon redemption processes where users
 * provide a coupon code to redeem their benefits.
 *
 * @summary Query to retrieve a coupon by its code
 * @param code The coupon redemption code
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetCouponByCodeQuery(String code) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetCouponByCodeQuery {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code cannot be null or empty");
        }
    }
}
