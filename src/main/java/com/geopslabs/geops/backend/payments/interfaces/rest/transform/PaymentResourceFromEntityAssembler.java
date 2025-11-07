package com.geopslabs.geops.backend.payments.interfaces.rest.transform;

import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.PaymentResource;

/**
 * PaymentResourceFromEntityAssembler
 *
 * Assembler class responsible for converting Payment entity objects
 * to PaymentResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer DTOs for API responses.
 *
 * @summary Converts Payment entity to PaymentResource
 * @since 1.0
 * @author GeOps Labs
 */
public class PaymentResourceFromEntityAssembler {

    /**
     * Converts a Payment entity to a PaymentResource.
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses.
     * It extracts all relevant payment information for client consumption.
     *
     * @param entity The Payment entity from the domain layer
     * @return A PaymentResource ready for REST API response
     */
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
            entity.getId(),
            entity.getUserId(),
            entity.getCartId(),
            entity.getAmount(),
            entity.getProductType(),
            entity.getProductId(),
            entity.getPaymentCodes(),
            entity.getPaymentMethod(),
            entity.getStatus(),
            entity.getCustomerEmail(),
            entity.getCustomerFirstName(),
            entity.getCustomerLastName(),
            entity.getPaymentCode(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null,
            entity.getCompletedAt()
        );
    }
}
