package com.geopslabs.geops.backend.notifications.interfaces.rest.transform;

import com.geopslabs.geops.backend.notifications.domain.model.commands.CreateNotificationCommand;
import com.geopslabs.geops.backend.notifications.interfaces.rest.resources.CreateNotificationResource;

/**
 * Create Notification Command From Resource Assembler
 *
 * Assembler to transform CreateNotificationResource to CreateNotificationCommand
 *
 * @summary Assembler for notification resource to command transformation
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateNotificationCommandFromResourceAssembler {

    /**
     * Transform CreateNotificationResource to CreateNotificationCommand
     *
     * @param resource CreateNotificationResource
     * @return CreateNotificationCommand
     */
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
            resource.userId(),
            resource.type(),
            resource.title(),
            resource.message(),
            resource.relatedEntityId(),
            resource.relatedEntityType(),
            resource.actionUrl()
        );
    }
}
