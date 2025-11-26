package com.geopslabs.geops.backend.notifications.application.internal.scheduledservices;

import com.geopslabs.geops.backend.coupons.infrastructure.persistence.jpa.CouponRepository;
import com.geopslabs.geops.backend.notifications.application.internal.outboundservices.NotificationFactoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Coupon Expiration Notification Service
 *
 * Scheduled service that checks for coupons expiring soon
 * and creates notifications for users
 *
 * @summary Service for coupon expiration notifications
 * @since 1.0
 * @author GeOps Labs
 */
@Service
public class CouponExpirationNotificationService {

    private final CouponRepository couponRepository;
    private final NotificationFactoryService notificationFactory;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public CouponExpirationNotificationService(
        CouponRepository couponRepository,
        NotificationFactoryService notificationFactory
    ) {
        this.couponRepository = couponRepository;
        this.notificationFactory = notificationFactory;
    }

    /**
     * Check for expiring coupons every day at 9:00 AM
     * Notifies users about coupons expiring within the next 3 days
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void checkExpiringCoupons() {
        try {
            var now = LocalDateTime.now();
            var threeDaysFromNow = now.plusDays(3);

            // Get all coupons
            var allCoupons = couponRepository.findAll();

            for (var coupon : allCoupons) {
                if (coupon.getExpiresAt() != null && !coupon.getExpiresAt().isBlank()) {
                    try {
                        var expiresAt = LocalDateTime.parse(coupon.getExpiresAt(), formatter);

                        // Check if coupon expires within next 3 days
                        if (expiresAt.isAfter(now) && expiresAt.isBefore(threeDaysFromNow)) {
                            // Create notification
                            notificationFactory.createCouponExpirationNotification(
                                Long.parseLong(coupon.getUserId()),
                                coupon.getId().toString(),
                                coupon.getCode()
                            );
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing expiration date for coupon " + coupon.getId() + ": " + e.getMessage());
                    }
                }
            }

            System.out.println("Coupon expiration check completed at " + now);
        } catch (Exception e) {
            System.err.println("Error checking expiring coupons: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
