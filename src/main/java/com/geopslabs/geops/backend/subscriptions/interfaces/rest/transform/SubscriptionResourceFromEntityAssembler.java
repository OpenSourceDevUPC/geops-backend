package com.geopslabs.geops.backend.subscriptions.interfaces.rest.transform;

import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources.SubscriptionResource;

/**
 * SubscriptionResourceFromEntityAssembler
 *
 * Assembler class responsible for converting Subscription entity objects
 * to SubscriptionResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer Resources for API responses.
 *
 * @summary Converts Subscription entity to SubscriptionResource
 * @since 1.0
 * @author GeOps Labs
 */
public class SubscriptionResourceFromEntityAssembler {

    /**
     * Converts a Subscription entity to a SubscriptionResource.
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses.
     * It extracts all relevant subscription information for client consumption.
     *
     * @param entity The Subscription entity from the domain layer
     * @return A SubscriptionResource ready for REST API response
     */
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
            entity.getId(),
            entity.getPrice(),
            // for primitive boolean the getter is 'isRecommended()'
            entity.isRecommended(),
            entity.getType(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }
}
