package com.geopslabs.geops.backend.coupons.domain.model.commands;

/**
 * UpdateCouponCommand
 *
 * Command record for updating an existing coupon.
 * This command allows partial updates of coupon data, including product type,
 * offer reference, coupon code, and expiration date.
 *
 * @summary Command to update an existing coupon
 * @param couponId The unique identifier of the coupon to update
 * @param productType Updated product type (optional)
 * @param offerId Updated reference to the offer id (optional)
 * @param code Updated coupon code to redeem (optional)
 * @param expiresAt Updated expiration date (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateCouponCommand(
    Long couponId,
    String productType,
    Long offerId,
    String code,
    String expiresAt
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateCouponCommand {
        if (couponId == null || couponId <= 0) {
            throw new IllegalArgumentException("couponId cannot be null or negative");
        }

        // Validate optional fields if provided
        if (code != null && code.isBlank()) {
            throw new IllegalArgumentException("code cannot be empty string if provided");
        }

        if (offerId != null && offerId <= 0) {
            throw new IllegalArgumentException("offerId must be positive if provided");
        }

        if (productType != null && productType.isBlank()) {
            throw new IllegalArgumentException("productType cannot be empty string if provided");
        }
    }
}
