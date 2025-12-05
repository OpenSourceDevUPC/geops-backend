package com.geopslabs.geops.backend.notifications.domain.model.valueObjects;

/**
 * Notification Type Enum
 *
 * Defines the different types of notifications in the system
 *
 * @summary Enumeration of notification types
 * @since 1.0
 * @author GeOps Labs
 */
public enum NotificationType {
    /**
     * Notification for payment completion
     */
    PAYMENT,

    /**
     * Notification for premium subscription upgrade with trial period
     */
    PREMIUM_UPGRADE,

    /**
     * Notification for user profile update
     */
    PROFILE_UPDATE,

    /**
     * Notification for favorite offer changes
     */
    FAVORITE,

    /**
     * Notification for coupon expiration
     */
    COUPON_EXPIRATION,

    /**
     * Notification for new review/comment on an offer
     */
    REVIEW_COMMENT
}
