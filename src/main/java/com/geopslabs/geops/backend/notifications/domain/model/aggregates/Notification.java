package com.geopslabs.geops.backend.notifications.domain.model.aggregates;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.notifications.domain.model.commands.CreateNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.valueObjects.NotificationType;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Notification Aggregate Root
 *
 * Represents a notification in the GeOps platform
 * Notifications are created for various events: payments, premium upgrades,
 * profile updates, favorites, coupon expirations, and review comments
 *
 * @summary Manages user notifications for platform events
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "notifications", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_is_read", columnList = "is_read"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Getter
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    /**
     * Reference to the user who owns this notification
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Notification type (PAYMENT, PREMIUM_UPGRADE, PROFILE_UPDATE, FAVORITE, COUPON_EXPIRATION, REVIEW_COMMENT)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    /**
     * Title of the notification
     */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /**
     * Message content of the notification
     */
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    /**
     * Indicates if the notification has been read
     */
    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    /**
     * Related entity ID (offer_id, coupon_id, payment_id, etc.)
     */
    @Column(name = "related_entity_id", length = 50)
    private String relatedEntityId;

    /**
     * Related entity type (OFFER, COUPON, PAYMENT, USER, REVIEW)
     */
    @Column(name = "related_entity_type", length = 50)
    private String relatedEntityType;

    /**
     * URL to navigate when notification is clicked
     */
    @Column(name = "action_url", length = 500)
    private String actionUrl;

    /**
     * Default constructor for JPA
     */
    protected Notification() {}

    /**
     * Creates a new Notification from a CreateNotificationCommand
     *
     * @param command The command containing notification creation data
     * @param user The user entity reference
     */
    public Notification(CreateNotificationCommand command, User user) {
        this.user = user;
        this.type = command.type();
        this.title = command.title();
        this.message = command.message();
        this.isRead = false;
        this.relatedEntityId = command.relatedEntityId();
        this.relatedEntityType = command.relatedEntityType();
        this.actionUrl = command.actionUrl();
    }

    /**
     * Gets the user ID for this notification
     *
     * @return The user ID
     */
    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    /**
     * Marks the notification as read
     */
    public void markAsRead() {
        this.isRead = true;
    }

    /**
     * Marks the notification as unread
     */
    public void markAsUnread() {
        this.isRead = false;
    }

    /**
     * Checks if notification is unread
     *
     * @return true if notification is unread
     */
    public boolean isUnread() {
        return !this.isRead;
    }
}
