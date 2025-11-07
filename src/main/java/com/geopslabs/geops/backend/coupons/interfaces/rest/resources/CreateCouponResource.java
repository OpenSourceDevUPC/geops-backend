package com.geopslabs.geops.backend.coupons.interfaces.rest.resources;

/**
 * CreateCouponResource
 *
 * Resource DTO for creating coupons via REST API.
 * This resource represents the request payload for coupon creation,
 * containing all necessary information for setting up a new coupon.
 * Based on the frontend Coupon entity structure.
 *
 * @summary Request resource for creating coupons
 * @param userId The unique identifier of the user who owns the coupon
 * @param paymentId The payment identifier that generated this coupon
 * @param paymentCode The payment code generated at payment time
 * @param productType The product type copied from payment (optional)
 * @param offerId The reference to the offer id (optional)
 * @param code The coupon code to redeem
 * @param expiresAt The expiration date of the coupon (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateCouponResource(
    String userId,
    String paymentId,
    String paymentCode,
    String productType,
    Long offerId,
    String code,
    String expiresAt
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateCouponResource {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("paymentId cannot be null or empty");
        }

        if (paymentCode == null || paymentCode.isBlank()) {
            throw new IllegalArgumentException("paymentCode cannot be null or empty");
        }

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code cannot be null or empty");
        }

        if (offerId != null && offerId <= 0) {
            throw new IllegalArgumentException("offerId must be positive if provided");
        }
    }
}
