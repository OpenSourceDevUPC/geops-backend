package com.geopslabs.geops.backend.notifications.application.internal.commandservices;

import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import com.geopslabs.geops.backend.notifications.domain.model.commands.CreateNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.commands.DeleteNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.geopslabs.geops.backend.notifications.domain.services.NotificationCommandService;
import com.geopslabs.geops.backend.notifications.infrastructure.persistence.jpa.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Notification Command Service Implementation
 *
 * Implementation of notification command service operations
 *
 * @summary Implementation of notification command operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository,
                                          UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        try {
            var userOptional = userRepository.findById(command.userId());
            
            if (userOptional.isEmpty()) {
                System.err.println("User with ID " + command.userId() + " not found");
                return Optional.empty();
            }

            var notification = new Notification(command, userOptional.get());
            var savedNotification = notificationRepository.save(notification);
            return Optional.of(savedNotification);
        } catch (Exception e) {
            System.err.println("Error creating notification: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Notification> handle(MarkNotificationAsReadCommand command) {
        try {
            var notificationOptional = notificationRepository.findById(command.notificationId());
            
            if (notificationOptional.isEmpty()) {
                System.err.println("Notification with ID " + command.notificationId() + " not found");
                return Optional.empty();
            }

            var notification = notificationOptional.get();
            notification.markAsRead();
            var updatedNotification = notificationRepository.save(notification);
            return Optional.of(updatedNotification);
        } catch (Exception e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeleteNotificationCommand command) {
        try {
            if (!notificationRepository.existsById(command.notificationId())) {
                System.err.println("Notification with ID " + command.notificationId() + " not found");
                return false;
            }

            notificationRepository.deleteById(command.notificationId());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting notification: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int markAllAsReadForUser(Long userId) {
        try {
            return notificationRepository.markAllAsReadByUserId(userId);
        } catch (Exception e) {
            System.err.println("Error marking all notifications as read for user: " + e.getMessage());
            return 0;
        }
    }
}
