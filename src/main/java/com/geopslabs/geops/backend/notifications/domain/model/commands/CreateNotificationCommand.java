package com.geopslabs.geops.backend.notifications.domain.model.commands;

import com.geopslabs.geops.backend.notifications.domain.model.valueObjects.NotificationType;

/**
 * Create Notification Command
 *
 * Command to create a new notification in the system
 *
 * @param userId User who will receive the notification
 * @param type Type of notification
 * @param title Notification title
 * @param message Notification message
 * @param relatedEntityId ID of related entity (optional)
 * @param relatedEntityType Type of related entity (optional)
 * @param actionUrl URL to navigate when clicked (optional)
 * @summary Command for creating notifications
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateNotificationCommand(
    Long userId,
    NotificationType type,
    String title,
    String message,
    String relatedEntityId,
    String relatedEntityType,
    String actionUrl
) {
    public CreateNotificationCommand {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Notification type cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
    }
}
