package com.geopslabs.geops.backend.notifications.domain.model.queries;

/**
 * Get Notifications By User ID Query
 *
 * Query to retrieve all notifications for a specific user
 *
 * @param userId User ID
 * @summary Query for getting user notifications
 * @since 1.0
 * @author GeOps Labs
 */
public record GetNotificationsByUserIdQuery(Long userId) {
    public GetNotificationsByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
