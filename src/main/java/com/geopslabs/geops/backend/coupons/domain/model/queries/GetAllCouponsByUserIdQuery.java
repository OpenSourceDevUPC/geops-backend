package com.geopslabs.geops.backend.coupons.domain.model.queries;

/**
 * GetAllCouponsByUserIdQuery
 *
 * Query record to retrieve all coupons for a specific user.
 * This query helps in displaying user coupon history, tracking user coupons,
 * and providing comprehensive coupon information for a particular user.
 *
 * @summary Query to retrieve all coupons for a specific user
 * @param userId The unique identifier of the user whose coupons to retrieve
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetAllCouponsByUserIdQuery(String userId) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetAllCouponsByUserIdQuery {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
    }
}
