package com.geopslabs.geops.backend.coupons.interfaces.rest.resources;

import java.util.List;

/**
 * CreateManyCouponsResource
 *
 * Resource Resource for creating multiple coupons via REST API.
 * This resource represents the request payload for bulk coupon creation,
 * supporting the frontend's createMany method that expects a bulk endpoint.
 *
 * @summary Request resource for creating multiple coupons
 * @param coupons List of CreateCouponResource objects to create
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateManyCouponsResource(
    List<CreateCouponResource> coupons
) {
    /**
     * Compact constructor that validates the resource parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public CreateManyCouponsResource {
        if (coupons == null || coupons.isEmpty()) {
            throw new IllegalArgumentException("coupons list cannot be null or empty");
        }

        if (coupons.size() > 100) {
            throw new IllegalArgumentException("cannot create more than 100 coupons at once");
        }
    }
}
