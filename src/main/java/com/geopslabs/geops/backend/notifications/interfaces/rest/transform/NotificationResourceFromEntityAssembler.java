package com.geopslabs.geops.backend.notifications.interfaces.rest.transform;

import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import com.geopslabs.geops.backend.notifications.interfaces.rest.resources.NotificationResource;

/**
 * Notification Resource From Entity Assembler
 *
 * Assembler to transform Notification entity to NotificationResource
 *
 * @summary Assembler for notification entity to resource transformation
 * @since 1.0
 * @author GeOps Labs
 */
public class NotificationResourceFromEntityAssembler {

    /**
     * Transform Notification entity to NotificationResource
     *
     * @param entity Notification entity
     * @return NotificationResource
     */
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
            entity.getId(),
            entity.getUserId(),
            entity.getType(),
            entity.getTitle(),
            entity.getMessage(),
            entity.getIsRead(),
            entity.getRelatedEntityId(),
            entity.getRelatedEntityType(),
            entity.getActionUrl(),
            entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null
        );
    }
}
