package com.geopslabs.geops.backend.notifications.domain.services;

import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import com.geopslabs.geops.backend.notifications.domain.model.commands.CreateNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.commands.DeleteNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.commands.MarkNotificationAsReadCommand;

import java.util.Optional;

/**
 * Notification Command Service
 *
 * Service interface for handling notification command operations
 *
 * @summary Interface for notification command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface NotificationCommandService {

    /**
     * Creates a new notification
     *
     * @param command The command containing notification data
     * @return Optional containing the created notification
     */
    Optional<Notification> handle(CreateNotificationCommand command);

    /**
     * Marks a notification as read
     *
     * @param command The command containing notification ID
     * @return Optional containing the updated notification
     */
    Optional<Notification> handle(MarkNotificationAsReadCommand command);

    /**
     * Deletes a notification
     *
     * @param command The command containing notification ID
     * @return true if deleted successfully, false otherwise
     */
    boolean handle(DeleteNotificationCommand command);

    /**
     * Marks all notifications as read for a user
     *
     * @param userId User ID
     * @return Number of notifications marked as read
     */
    int markAllAsReadForUser(Long userId);
}
