package com.geopslabs.geops.backend.payments.interfaces.rest.transform;

import com.geopslabs.geops.backend.payments.domain.model.commands.UpdatePaymentStatusCommand;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.UpdatePaymentStatusResource;

/**
 * UpdatePaymentStatusCommandFromResourceAssembler
 *
 * Assembler class responsible for converting UpdatePaymentStatusResource objects
 * to UpdatePaymentStatusCommand objects. This transformation follows the DDD pattern
 * of converting interface layer Resources to domain layer commands.
 *
 * @summary Converts UpdatePaymentStatusResource to UpdatePaymentStatusCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class UpdatePaymentStatusCommandFromResourceAssembler {

    /**
     * Converts an UpdatePaymentStatusResource to an UpdatePaymentStatusCommand.
     *
     * This method transforms the REST API resource representation into
     * a domain command that can be processed by the domain services.
     * The payment ID is provided separately as it typically comes from the URL path.
     *
     * @param paymentId The unique identifier of the payment to update
     * @param resource The UpdatePaymentStatusResource from the REST API request
     * @return An UpdatePaymentStatusCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static UpdatePaymentStatusCommand toCommandFromResource(Long paymentId, UpdatePaymentStatusResource resource) {
        return new UpdatePaymentStatusCommand(
            paymentId,
            resource.status(),
            resource.completedAt()
        );
    }
}
