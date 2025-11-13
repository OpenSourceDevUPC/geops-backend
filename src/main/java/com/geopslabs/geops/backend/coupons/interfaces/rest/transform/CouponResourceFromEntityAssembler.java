package com.geopslabs.geops.backend.coupons.interfaces.rest.transform;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CouponResource;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.interfaces.rest.transform.OfferResourceFromEntityAssembler;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.OfferResource;

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
     * Converts a Coupon entity to a CouponResource without embedded offer data.
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses.
     * It extracts all relevant coupon information for client consumption.
     *
     * @param entity The Coupon entity from the domain layer
     * @return A CouponResource ready for REST API response
     */
    public static CouponResource toResourceFromEntity(Coupon entity) {
        return toResourceFromEntityWithOffer(entity, null);
    }

    /**
     * Converts a Coupon entity to a CouponResource and embeds Offer data when provided.
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses.
     * It extracts all relevant coupon information for client consumption,
     * and if an Offer is provided, it embeds the corresponding offer data
     * into the CouponResource.
     *
     * @param entity The Coupon entity from the domain layer
     * @param offer  Optional Offer aggregate to embed inside the resource (may be null)
     * @return A CouponResource ready for REST API response with optional offer
     */
    public static CouponResource toResourceFromEntityWithOffer(Coupon entity, Offer offer) {
        OfferResource offerResource = null;
        if (offer != null) {
            offerResource = OfferResourceFromEntityAssembler.toResourceFromEntity(offer);
        }

        return new CouponResource(
            entity.getId(),
            entity.getUserId(),
            entity.getPaymentId(),
            entity.getPaymentCode(),
            entity.getProductType(),
            entity.getOfferId(),
            offerResource,
            entity.getCode(),
            entity.getExpiresAt(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }
}
