package com.geopslabs.geops.backend.notifications.domain.model.commands;

/**
 * Mark Notification As Read Command
 *
 * Command to mark a notification as read
 *
 * @param notificationId ID of the notification to mark as read
 * @summary Command for marking notification as read
 * @since 1.0
 * @author GeOps Labs
 */
public record MarkNotificationAsReadCommand(Long notificationId) {
    public MarkNotificationAsReadCommand {
        if (notificationId == null) {
            throw new IllegalArgumentException("Notification ID cannot be null");
        }
    }
}
