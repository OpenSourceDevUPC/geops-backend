package com.geopslabs.geops.backend.coupons.interfaces.rest.resources;

/**
 * CouponResource
 *
 * Resource DTO for coupon responses via REST API.
 * This resource represents the response payload containing coupon information
 * when retrieving coupon data from the system.
 * Based on the frontend Coupon entity structure.
 *
 * @summary Response resource for coupon data
 * @param id The unique identifier of the coupon
 * @param userId The unique identifier of the user who owns the coupon
 * @param paymentId The payment identifier that generated this coupon
 * @param paymentCode The payment code generated at payment time
 * @param productType The product type copied from payment (optional)
 * @param offerId The reference to the offer id (optional)
 * @param code The coupon code to redeem
 * @param expiresAt The expiration date of the coupon (optional)
 * @param createdAt Timestamp when the coupon was created
 * @param updatedAt Timestamp when the coupon was last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CouponResource(
    Long id,
    String userId,
    String paymentId,
    String paymentCode,
    String productType,
    Long offerId,
    String code,
    String expiresAt,
    String createdAt,
    String updatedAt
) {
    // This record doesn't need validation in the compact constructor
    // as it's used for response data that should already be validated
}
