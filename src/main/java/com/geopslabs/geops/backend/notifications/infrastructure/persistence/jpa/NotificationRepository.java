package com.geopslabs.geops.backend.notifications.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Notification Repository
 *
 * JPA repository for Notification aggregate
 *
 * @summary Repository for notification persistence operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find all notifications for a specific user, ordered by creation date descending
     *
     * @param userId User ID
     * @return List of notifications
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Count unread notifications for a user
     *
     * @param userId User ID
     * @param isRead Read status (false for unread)
     * @return Count of unread notifications
     */
    Long countByUserIdAndIsRead(Long userId, Boolean isRead);

    /**
     * Find all unread notifications for a user
     *
     * @param userId User ID
     * @param isRead Read status
     * @return List of notifications
     */
    List<Notification> findByUserIdAndIsReadOrderByCreatedAtDesc(Long userId, Boolean isRead);

    /**
     * Mark all notifications as read for a user
     *
     * @param userId User ID
     * @return Number of updated notifications
     */
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.userId = :userId AND n.isRead = false")
    int markAllAsReadByUserId(@Param("userId") Long userId);
}
