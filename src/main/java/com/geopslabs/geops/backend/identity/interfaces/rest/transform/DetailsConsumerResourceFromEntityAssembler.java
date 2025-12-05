package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.DetailsConsumerResource;

/**
 * DetailsConsumerResourceFromEntityAssembler
 *
 * Assembler class that transforms DetailsConsumer entities to DetailsConsumerResource Resources
 * This class follows the Assembler pattern to separate domain objects from REST representations
 *
 * @summary Transforms DetailsConsumer entities to resource Resources
 * @since 1.0
 * @author GeOps Labs
 */
public class DetailsConsumerResourceFromEntityAssembler {

    /**
     * Transforms a DetailsConsumer entity to a DetailsConsumerResource
     *
     * @param entity The DetailsConsumer entity to transform
     * @return The corresponding DetailsConsumerResource Resource
     */
    public static DetailsConsumerResource toResourceFromEntity(DetailsConsumer entity) {
        return new DetailsConsumerResource(
            entity.getId(),
            entity.getUser().getId(),
            entity.getCategoriasFavoritas(),
            entity.getRecibirNotificaciones(),
            entity.getPermisoUbicacion(),
            entity.getDireccionCasa(),
            entity.getDireccionTrabajo(),
            entity.getDireccionUniversidad(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}

