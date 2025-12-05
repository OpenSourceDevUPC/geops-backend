package com.geopslabs.geops.backend.coupons.domain.services;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetAllCouponsByUserIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByCodeQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponsByPaymentIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * CouponQueryService
 *
 * Domain service interface that defines query operations for coupon management.
 * This service handles all read operations following the Command Query Responsibility
 * Segregation (CQRS) pattern, providing various ways to retrieve coupon data.
 *
 * @summary Service interface for handling coupon query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface CouponQueryService {

    /**
     * Handles the query to retrieve a coupon by its unique identifier.
     *
     * This method processes the query to find a specific coupon using its ID.
     * It's commonly used for coupon validation, redemption, and detailed views.
     *
     * @param query The query containing the coupon ID
     * @return An Optional containing the Coupon if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Coupon> handle(GetCouponByIdQuery query);

    /**
     * Handles the query to retrieve all coupons for a specific user.
     *
     * This method processes the query to find all coupons belonging to a user.
     * It's useful for displaying user coupon history and coupon management.
     *
     * @param query The query containing the user ID
     * @return A List of Coupon objects for the specified user
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Coupon> handle(GetAllCouponsByUserIdQuery query);

    /**
     * Handles the query to retrieve coupons generated from a specific payment.
     *
     * This method processes the query to find coupons associated with a payment.
     * It's useful for tracking coupon generation and payment-coupon relationships.
     *
     * @param query The query containing the payment ID
     * @return A List of Coupon objects generated from the specified payment
     * @throws IllegalArgumentException if the query contains invalid data
     */
    List<Coupon> handle(GetCouponsByPaymentIdQuery query);

    /**
     * Handles the query to retrieve a coupon by its redemption code.
     *
     * This method processes the query to find a coupon using its unique code.
     * It's essential for coupon redemption processes and validation.
     *
     * @param query The query containing the coupon code
     * @return An Optional containing the Coupon if found, empty otherwise
     * @throws IllegalArgumentException if the query contains invalid data
     */
    Optional<Coupon> handle(GetCouponByCodeQuery query);

    /**
     * Retrieves all coupons in the system.
     *
     * This method provides a comprehensive view of all coupons,
     * useful for administrative dashboards and reporting purposes.
     *
     * @return A List of all Coupon objects in the system
     */
    List<Coupon> getAllCoupons();

    /**
     * Retrieves all valid (non-expired) coupons for a specific user.
     *
     * This method finds only valid coupons for a user,
     * useful for displaying redeemable coupons in user interfaces.
     *
     * @param userId The unique identifier of the user
     * @return A List of valid Coupon objects for the specified user
     * @throws IllegalArgumentException if userId is null or empty
     */
    List<Coupon> getValidCouponsByUserId(Long userId);

    /**
     * Retrieves expired coupons for cleanup or analysis purposes.
     *
     * This method finds coupons that have passed their expiration date,
     * useful for cleanup operations and expired coupon management.
     *
     * @return A List of expired Coupon objects
     */
    List<Coupon> getExpiredCoupons();
}
