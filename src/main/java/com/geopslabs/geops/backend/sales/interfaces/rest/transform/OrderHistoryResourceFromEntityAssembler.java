package com.geopslabs.geops.backend.sales.interfaces.rest.transform;

import com.geopslabs.geops.backend.sales.domain.model.aggregates.OrderHistory;
import com.geopslabs.geops.backend.sales.interfaces.rest.resources.OrderHistoryDetailResource;
import com.geopslabs.geops.backend.sales.interfaces.rest.resources.OrderHistoryResource;

/**
 * OrderHistoryResourceFromEntityAssembler
 * Assembler class responsible for converting OrderHistory entity objects
 * to Resource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer Resources for API responses
 *
 * @summary Converts OrderHistory entity to Resources
 * @since 1.0
 * @author GeOps Labs
 */
public class OrderHistoryResourceFromEntityAssembler {

    /**
     * Converts an OrderHistory entity to an OrderHistoryResource (summary view)
     * This method transforms the domain entity representation into
     * a REST API resource for list/table views
     *
     * @param entity The OrderHistory entity from the domain layer
     * @return An OrderHistoryResource ready for REST API response
     */
    public static OrderHistoryResource toResourceFromEntity(OrderHistory entity) {
        if (entity == null) {
            return null;
        }

        return new OrderHistoryResource(
            entity.getId(),
            entity.getPaymentId(),
            entity.getCustomerName(),
            entity.getOfferTitle(),
            entity.getPartnerName(),
            entity.getPurchasePrice(),
            entity.getPurchaseDate(),
            entity.getPaymentStatus() != null ? entity.getPaymentStatus().toString() : null,
            entity.getTotalCoupons(),
            entity.getRedeemedCoupons(),
            entity.getPendingCoupons(),
            entity.getExpiredCoupons(),
            generateCouponStatus(
                entity.getTotalCoupons(),
                entity.getRedeemedCoupons(),
                entity.getExpiredCoupons()
            )
        );
    }

    /**
     * Converts an OrderHistory entity to an OrderHistoryDetailResource (detailed view)
     * This method transforms the domain entity representation into
     * a REST API resource with all details for expanded/detailed views
     *
     * @param entity The OrderHistory entity from the domain layer
     * @return An OrderHistoryDetailResource ready for REST API response
     */
    public static OrderHistoryDetailResource toDetailResourceFromEntity(OrderHistory entity) {
        if (entity == null) {
            return null;
        }

        return new OrderHistoryDetailResource(
            entity.getId(),
            entity.getPaymentId(),
            entity.getUserId(),
            entity.getSupplierId(),
            entity.getOfferId(),
            entity.getCartId(),
            entity.getCustomerName(),
            entity.getCustomerEmail(),
            entity.getCustomerFirstName(),
            entity.getCustomerLastName(),
            entity.getPurchasePrice(),
            entity.getPaymentMethod() != null ? entity.getPaymentMethod().toString() : null,
            entity.getPaymentCode(),
            entity.getPaymentStatus() != null ? entity.getPaymentStatus().toString() : null,
            entity.getPurchaseDate(),
            entity.getCompletedAt(),
            entity.getPaymentCodes(),
            entity.getOfferTitle(),
            entity.getPartnerName(),
            entity.getCategory(),
            entity.getLocation(),
            entity.getImageUrl(),
            entity.getOfferValidTo(),
            entity.getTotalCoupons(),
            entity.getRedeemedCoupons(),
            entity.getPendingCoupons(),
            entity.getExpiredCoupons(),
            generateCouponStatus(
                entity.getTotalCoupons(),
                entity.getRedeemedCoupons(),
                entity.getExpiredCoupons()
            ),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    /**
     * Generates a visual summary of the redemption status
     *
     * Examples:
     * - "5 de 5 ✅" (all redeemed)
     * - "2 de 5 ⏳" (some pending)
     * - "0 canjeados, 1 expirados" (with expired)
     *
     * @param total Total coupons
     * @param redeemed Redeemed coupons
     * @param expired Expired coupons
     * @return String with status summary
     */
    private static String generateCouponStatus(Integer total, Integer redeemed, Integer expired) {
        if (total == null || total == 0) {
            return "Sin cupones";
        }

        // If all are redeemed
        if (redeemed != null && redeemed.equals(total)) {
            return redeemed + " de " + total + " ✅";
        }

        // If some are redeemed
        if (redeemed != null && redeemed > 0) {
            return redeemed + " de " + total + " ⏳";
        }

        // If none redeemed but some expired
        if (expired != null && expired > 0) {
            return "0 canjeados, " + expired + " expirados";
        }

        // If none redeemed
        return "0 de " + total;
    }
}

