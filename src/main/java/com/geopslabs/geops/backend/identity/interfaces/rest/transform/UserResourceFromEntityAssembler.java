package com.geopslabs.geops.backend.identity.interfaces.rest.transform;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UserResource;

/**
 * UserResourceFromEntityAssembler
 *
 * Assembler class responsible for converting User entity objects
 * to UserResource objects. This transformation follows the DDD pattern
 * of converting domain layer entities to interface layer DTOs for API responses
 *
 * @summary Converts User entity to UserResource
 * @since 1.0
 * @author GeOps Labs
 */
public class UserResourceFromEntityAssembler {

    /**
     * Converts a User entity to a UserResource
     *
     * This method transforms the domain entity representation into
     * a REST API resource that can be returned in HTTP responses
     * It extracts all relevant user information for client consumption
     * excluding sensitive data like passwords
     *
     * @param entity The User entity from the domain layer
     * @return A UserResource ready for REST API response
     */
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getRole(),
            entity.getPlan(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}

