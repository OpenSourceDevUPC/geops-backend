package com.geopslabs.geops.backend.coupons.domain.model.commands;

import java.util.List;

/**
 * CreateManyCouponsCommand
 *
 * Command record for creating multiple coupons in a single operation.
 * This command is designed to support bulk coupon creation from the frontend
 * createMany method that expects a bulk endpoint.
 *
 * @summary Command to create multiple coupons at once
 * @param coupons List of CreateCouponCommand objects to create
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateManyCouponsCommand(
    List<CreateCouponCommand> coupons
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateManyCouponsCommand {
        if (coupons == null || coupons.isEmpty()) {
            throw new IllegalArgumentException("coupons list cannot be null or empty");
        }

        if (coupons.size() > 100) {
            throw new IllegalArgumentException("cannot create more than 100 coupons at once");
        }
    }
}
