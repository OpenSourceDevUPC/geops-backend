package com.geopslabs.geops.backend.notifications.domain.model.commands;

/**
 * Delete Notification Command
 *
 * Command to delete a notification
 *
 * @param notificationId ID of the notification to delete
 * @summary Command for deleting notifications
 * @since 1.0
 * @author GeOps Labs
 */
public record DeleteNotificationCommand(Long notificationId) {
    public DeleteNotificationCommand {
        if (notificationId == null) {
            throw new IllegalArgumentException("Notification ID cannot be null");
        }
    }
}
