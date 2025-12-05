package com.geopslabs.geops.backend.notifications.application.internal.outboundservices;

import com.geopslabs.geops.backend.notifications.domain.model.valueObjects.NotificationType;
import com.geopslabs.geops.backend.notifications.domain.model.commands.CreateNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.services.NotificationCommandService;
import org.springframework.stereotype.Service;

/**
 * Notification Factory Service
 *
 * Service to create notifications for different events in the system
 *
 * @summary Factory service for creating notifications
 * @since 1.0
 * @author GeOps Labs
 */
@Service
public class NotificationFactoryService {

    private final NotificationCommandService notificationCommandService;

    public NotificationFactoryService(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Create notification for payment completion
     */
    public void createPaymentNotification(Long userId, String paymentId, String amount) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.PAYMENT,
            "Pago realizado",
            "Tu pago de " + amount + " ha sido procesado exitosamente",
            paymentId,
            "PAYMENT",
            "/payments/" + paymentId
        );
        notificationCommandService.handle(command);
    }

    /**
     * Create notification for premium upgrade with trial
     */
    public void createPremiumUpgradeNotification(Long userId) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.PREMIUM_UPGRADE,
            "¡Bienvenido a Premium!",
            "Tu suscripción Premium está activa. Disfruta de 1 mes de prueba gratuita",
            userId.toString(),
            "USER",
            "/profile"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Create notification for profile update
     */
    public void createProfileUpdateNotification(Long userId) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.PROFILE_UPDATE,
            "Perfil actualizado",
            "Tu información de perfil ha sido actualizada correctamente",
            userId.toString(),
            "USER",
            "/profile"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Create notification for favorite added
     */
    public void createFavoriteAddedNotification(Long userId, String offerId, String offerTitle) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.FAVORITE,
            "Oferta agregada a favoritos",
            "Has agregado \"" + offerTitle + "\" a tus favoritos",
            offerId,
            "OFFER",
            "/ofertas/" + offerId
        );
        notificationCommandService.handle(command);
    }

    /**
     * Create notification for coupon expiration
     */
    public void createCouponExpirationNotification(Long userId, String couponId, String couponCode) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.COUPON_EXPIRATION,
            "Cupón por vencer",
            "Tu cupón \"" + couponCode + "\" está por vencer pronto. ¡Úsalo antes de que expire!",
            couponId,
            "COUPON",
            "/mis-cupones"
        );
        notificationCommandService.handle(command);
    }

    /**
     * Create notification for new review comment
     */
    public void createReviewCommentNotification(Long userId, Long offerId, String offerTitle, String reviewerName) {
        var command = new CreateNotificationCommand(
            userId,
            NotificationType.REVIEW_COMMENT,
            "Nuevo comentario en oferta",
            reviewerName + " ha comentado en \"" + offerTitle + "\"",
            offerId.toString(),
            "OFFER",
            "/ofertas/" + offerId
        );
        notificationCommandService.handle(command);
    }
}
