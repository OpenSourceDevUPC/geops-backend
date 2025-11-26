package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UserResource;

/**
 * UserResourceFromEntityAssembler
 *
 * Assembler class that transforms User entities to UserResource DTOs
 * This class follows the Assembler pattern to separate domain objects from REST representations
 *
 * @summary Transforms User entities to resource DTOs
 * @since 1.0
 * @author GeOps Labs
 */
public class UserResourceFromEntityAssembler {

    /**
     * Transforms a User entity to a UserResource
     *
     * @param entity The User entity to transform
     * @return The corresponding UserResource DTO
     */
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getRole(),
            entity.getPlan(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}

