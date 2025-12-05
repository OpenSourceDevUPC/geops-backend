package com.geopslabs.geops.backend.offers.interfaces.rest.transform;

import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.OfferResource;

/**
 * OfferResourceFromEntityAssembler
 * Assembler class responsible for converting Offer entity objects
 * to OfferResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer Resources for API responses
 *
 * @summary Converts Offer entity to OfferResource
 * @since 1.0
 * @author GeOps Labs
 */
public class OfferResourceFromEntityAssembler {

    /**
     * Converts an Offer entity to an OfferResource
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses
     * It extracts all relevant offer information for client consumption
     *
     * @param entity The Offer entity from the domain layer
     * @return An OfferResource ready for REST API response
     */
    public static OfferResource toResourceFromEntity(Offer entity) {
        return new OfferResource(
            entity.getId(),
            entity.getCampaign().getId(),
            entity.getTitle(),
            entity.getPartner(),
            entity.getPrice(),
            entity.getCodePrefix(),
            entity.getValidTo(),
            entity.getRating(),
            entity.getLocation(),
            entity.getCategory(),
            entity.getImageUrl(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
            entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }
}
