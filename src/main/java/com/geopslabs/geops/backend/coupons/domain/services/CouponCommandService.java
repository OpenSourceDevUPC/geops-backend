package com.geopslabs.geops.backend.coupons.domain.services;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateManyCouponsCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.UpdateCouponCommand;

import java.util.List;
import java.util.Optional;

/**
 * CouponCommandService
 *
 * Domain service interface that defines command operations for coupon management.
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern.
 *
 * @summary Service interface for handling coupon command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface CouponCommandService {

    /**
     * Handles the creation of a new coupon.
     *
     * This method processes the command to create a new coupon, validates the input,
     * and persists the coupon data. It supports coupon generation from payments
     * and offer redemption scenarios.
     *
     * @param command The command containing all necessary data for coupon creation
     * @return An Optional containing the created Coupon if successful, empty if failed
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Coupon> handle(CreateCouponCommand command);

    /**
     * Handles the creation of multiple coupons in a single operation.
     *
     * This method processes the bulk creation command to create multiple coupons
     * at once, which is useful for batch operations and improved performance.
     * It corresponds to the frontend's createMany method that expects a bulk endpoint.
     *
     * @param command The command containing multiple coupon creation data
     * @return A List containing the created Coupons
     * @throws IllegalArgumentException if the command contains invalid data
     */
    List<Coupon> handle(CreateManyCouponsCommand command);

    /**
     * Handles the update of an existing coupon.
     *
     * This method processes the command to update coupon data such as product type,
     * offer reference, coupon code, and expiration date. It performs partial updates
     * based on provided fields.
     *
     * @param command The command containing the coupon ID and updated data
     * @return An Optional containing the updated Coupon if successful, empty if not found
     * @throws IllegalArgumentException if the command contains invalid data
     */
    Optional<Coupon> handle(UpdateCouponCommand command);

    /**
     * Deletes a coupon by its ID.
     *
     * This method permanently removes a coupon from the system.
     * This operation should be used with caution as it cannot be undone.
     *
     * @param couponId The unique identifier of the coupon to delete
     * @return true if the coupon was successfully deleted, false if not found
     * @throws IllegalArgumentException if the coupon ID is invalid
     */
    boolean deleteCoupon(Long couponId);
}
