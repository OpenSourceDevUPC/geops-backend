package com.geopslabs.geops.backend.notifications.interfaces.rest.resources;

import com.geopslabs.geops.backend.notifications.domain.model.valueObjects.NotificationType;

/**
 * Create Notification Resource
 *
 * REST resource for creating a notification
 *
 * @param userId User ID
 * @param type Notification type
 * @param title Notification title
 * @param message Notification message
 * @param relatedEntityId Related entity ID (optional)
 * @param relatedEntityType Related entity type (optional)
 * @param actionUrl Action URL (optional)
 * @summary REST resource for notification creation
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateNotificationResource(
    Long userId,
    NotificationType type,
    String title,
    String message,
    String relatedEntityId,
    String relatedEntityType,
    String actionUrl
) {}
