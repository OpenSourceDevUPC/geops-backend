package com.geopslabs.geops.backend.coupons.domain.model.queries;

/**
 * GetCouponByIdQuery
 *
 * Query record to retrieve a coupon by its unique identifier.
 * This query is used to fetch specific coupon details when needed
 * for validation, redemption, or displaying coupon information.
 *
 * @summary Query to retrieve a coupon by its ID
 * @param couponId The unique identifier of the coupon
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetCouponByIdQuery(Long couponId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetCouponByIdQuery {
        if (couponId == null || couponId <= 0) {
            throw new IllegalArgumentException("couponId cannot be null or negative");
        }
    }
}
