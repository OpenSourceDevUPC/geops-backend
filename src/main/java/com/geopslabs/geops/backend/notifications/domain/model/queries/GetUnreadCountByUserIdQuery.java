package com.geopslabs.geops.backend.notifications.domain.model.queries;

/**
 * Get Unread Count By User ID Query
 *
 * Query to retrieve count of unread notifications for a user
 *
 * @param userId User ID
 * @summary Query for getting unread notification count
 * @since 1.0
 * @author GeOps Labs
 */
public record GetUnreadCountByUserIdQuery(Long userId) {
    public GetUnreadCountByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
