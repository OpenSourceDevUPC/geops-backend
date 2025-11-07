package com.geopslabs.geops.backend.payments.interfaces.rest.transform;

import com.geopslabs.geops.backend.payments.domain.model.commands.CreatePaymentCommand;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.CreatePaymentResource;

/**
 * CreatePaymentCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreatePaymentResource objects
 * to CreatePaymentCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands.
 *
 * @summary Converts CreatePaymentResource to CreatePaymentCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreatePaymentCommandFromResourceAssembler {

    /**
     * Converts a CreatePaymentResource to a CreatePaymentCommand.
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services.
     * All validation is handled at the command level.
     *
     * @param resource The CreatePaymentResource from the REST API request
     * @return A CreatePaymentCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(
            resource.userId(),
            resource.cartId(),
            resource.amount(),
            resource.productType(),
            resource.productId(),
            resource.paymentCodes(),
            resource.paymentMethod(),
            resource.customerEmail(),
            resource.customerFirstName(),
            resource.customerLastName(),
            resource.paymentCode()
        );
    }
}
