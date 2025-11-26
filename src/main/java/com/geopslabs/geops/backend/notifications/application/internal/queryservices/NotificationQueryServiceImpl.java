package com.geopslabs.geops.backend.notifications.application.internal.queryservices;

import com.geopslabs.geops.backend.notifications.domain.model.aggregates.Notification;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetNotificationByIdQuery;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetNotificationsByUserIdQuery;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetUnreadCountByUserIdQuery;
import com.geopslabs.geops.backend.notifications.domain.services.NotificationQueryService;
import com.geopslabs.geops.backend.notifications.infrastructure.persistence.jpa.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Notification Query Service Implementation
 *
 * Implementation of notification query service operations
 *
 * @summary Implementation of notification query operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(query.userId());
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.notificationId());
    }

    @Override
    public Long handle(GetUnreadCountByUserIdQuery query) {
        return notificationRepository.countByUserIdAndIsRead(query.userId(), false);
    }
}
