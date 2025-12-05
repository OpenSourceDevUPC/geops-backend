package com.geopslabs.geops.backend.notifications.domain.services;

import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetNotificationByIdQuery;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetNotificationsByUserIdQuery;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetUnreadCountByUserIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Notification Query Service
 *
 * Service interface for handling notification query operations
 *
 * @summary Interface for notification query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface NotificationQueryService {

    /**
     * Gets all notifications for a user
     *
     * @param query The query containing user ID
     * @return List of notifications
     */
    List<Notification> handle(GetNotificationsByUserIdQuery query);

    /**
     * Gets a specific notification by ID
     *
     * @param query The query containing notification ID
     * @return Optional containing the notification
     */
    Optional<Notification> handle(GetNotificationByIdQuery query);

    /**
     * Gets count of unread notifications for a user
     *
     * @param query The query containing user ID
     * @return Count of unread notifications
     */
    Long handle(GetUnreadCountByUserIdQuery query);
}
