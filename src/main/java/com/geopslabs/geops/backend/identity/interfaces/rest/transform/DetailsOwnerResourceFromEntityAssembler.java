package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.DetailsOwnerResource;

/**
 * DetailsOwnerResourceFromEntityAssembler
 *
 * Assembler class that transforms DetailsOwner entities to DetailsOwnerResource Resources
 * This class follows the Assembler pattern to separate domain objects from REST representations
 *
 * @summary Transforms DetailsOwner entities to resource Resources
 * @since 1.0
 * @author GeOps Labs
 */
public class DetailsOwnerResourceFromEntityAssembler {

    /**
     * Transforms a DetailsOwner entity to a DetailsOwnerResource
     *
     * @param entity The DetailsOwner entity to transform
     * @return The corresponding DetailsOwnerResource Resource
     */
    public static DetailsOwnerResource toResourceFromEntity(DetailsOwner entity) {
        return new DetailsOwnerResource(
            entity.getId(),
            entity.getUser().getId(),
            entity.getBusinessName(),
            entity.getBusinessType(),
            entity.getTaxId(),
            entity.getWebsite(),
            entity.getDescription(),
            entity.getAddress(),
            entity.getHorarioAtencion(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}

