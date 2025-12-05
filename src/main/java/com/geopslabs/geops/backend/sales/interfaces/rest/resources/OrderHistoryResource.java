package com.geopslabs.geops.backend.sales.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderHistoryResource
 * Resource for general order history responses via REST API
 * This resource represents the summary view shown in tables/lists
 *
 * @summary Response resource for order history summary data
 * @param id The unique identifier of the order history
 * @param paymentId The payment identifier
 * @param customerName The customer's full name
 * @param offerTitle The title of the purchased offer
 * @param partnerName The partner/supplier name
 * @param purchasePrice The purchase price
 * @param purchaseDate The date of purchase
 * @param paymentStatus The payment status (PENDING, COMPLETED, FAILED)
 * @param totalCoupons Total coupons generated
 * @param redeemedCoupons Coupons already redeemed
 * @param pendingCoupons Coupons still available
 * @param expiredCoupons Coupons that have expired
 * @param couponStatus Summary status of coupon redemption
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record OrderHistoryResource(
    Long id,
    String paymentId,
    String customerName,
    String offerTitle,
    String partnerName,
    BigDecimal purchasePrice,
    Date purchaseDate,
    String paymentStatus,
    Integer totalCoupons,
    Integer redeemedCoupons,
    Integer pendingCoupons,
    Integer expiredCoupons,
    String couponStatus
) {
}


