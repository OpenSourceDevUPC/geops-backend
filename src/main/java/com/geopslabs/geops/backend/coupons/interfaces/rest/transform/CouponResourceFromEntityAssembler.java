package com.geopslabs.geops.backend.coupons.interfaces.rest.transform;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CouponResource;

/**
 * CouponResourceFromEntityAssembler
 *
 * Assembler class responsible for converting Coupon entity objects
 * to CouponResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer DTOs for API responses.
 *
 * @summary Converts Coupon entity to CouponResource
 * @since 1.0
 * @author GeOps Labs
 */
public class CouponResourceFromEntityAssembler {

    /**
     * Converts a Coupon entity to a CouponResource.
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses.
     * It extracts all relevant coupon information for client consumption.
     *
     * @param entity The Coupon entity from the domain layer
     * @return A CouponResource ready for REST API response
     */
    public static CouponResource toResourceFromEntity(Coupon entity) {
        return new CouponResource(
            entity.getId(),
            entity.getUserId(),
            entity.getPaymentId(),
            entity.getPaymentCode(),
            entity.getProductType(),
            entity.getOfferId(),
            entity.getCode(),
            entity.getExpiresAt(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }
}
