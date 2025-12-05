package com.geopslabs.geops.backend.sales.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * OrderHistoryDetailResource
 * Resource for detailed order history responses via REST API
 * This resource represents the complete view with all order details
 *
 * @summary Response resource for detailed order history data
 * @param id The unique identifier of the order history
 * @param paymentId The payment identifier
 * @param userId The buyer's user identifier
 * @param supplierId The supplier/provider identifier
 * @param offerId The offer identifier
 * @param cartId The cart identifier
 * @param customerName The customer's full name
 * @param customerEmail The customer's email
 * @param customerFirstName The customer's first name
 * @param customerLastName The customer's last name
 * @param purchasePrice The purchase price
 * @param paymentMethod The payment method (YAPE, CARD, BANK_TRANSFER)
 * @param paymentCode The payment reference code
 * @param paymentStatus The payment status (PENDING, COMPLETED, FAILED)
 * @param purchaseDate The date of purchase
 * @param completedAt The date when payment was completed
 * @param paymentCodes The generated payment codes (JSON)
 * @param offerTitle The title of the offer
 * @param partnerName The partner/supplier name
 * @param category The offer category
 * @param location The location where the offer is valid
 * @param imageUrl The offer's image URL
 * @param offerValidTo The offer expiration date
 * @param totalCoupons Total coupons generated
 * @param redeemedCoupons Coupons already redeemed
 * @param pendingCoupons Coupons still available
 * @param expiredCoupons Coupons that have expired
 * @param couponStatus Summary status of coupon redemption
 * @param createdAt Record creation timestamp
 * @param updatedAt Record last update timestamp
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record OrderHistoryDetailResource(
    Long id,
    String paymentId,
    String userId,
    String supplierId,
    Long offerId,
    String cartId,
    String customerName,
    String customerEmail,
    String customerFirstName,
    String customerLastName,
    BigDecimal purchasePrice,
    String paymentMethod,
    String paymentCode,
    String paymentStatus,
    Date purchaseDate,
    Date completedAt,
    String paymentCodes,
    String offerTitle,
    String partnerName,
    String category,
    String location,
    String imageUrl,
    LocalDate offerValidTo,
    Integer totalCoupons,
    Integer redeemedCoupons,
    Integer pendingCoupons,
    Integer expiredCoupons,
    String couponStatus,
    Date createdAt,
    Date updatedAt
) {
}

