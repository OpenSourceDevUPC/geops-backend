package com.geopslabs.geops.backend.notifications.interfaces.rest;

import com.geopslabs.geops.backend.notifications.domain.model.commands.DeleteNotificationCommand;
import com.geopslabs.geops.backend.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetNotificationsByUserIdQuery;
import com.geopslabs.geops.backend.notifications.domain.model.queries.GetUnreadCountByUserIdQuery;
import com.geopslabs.geops.backend.notifications.domain.services.NotificationCommandService;
import com.geopslabs.geops.backend.notifications.domain.services.NotificationQueryService;
import com.geopslabs.geops.backend.notifications.interfaces.rest.resources.CreateNotificationResource;
import com.geopslabs.geops.backend.notifications.interfaces.rest.resources.NotificationResource;
import com.geopslabs.geops.backend.notifications.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.geopslabs.geops.backend.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Notification Controller
 *
 * REST controller for notification operations
 *
 * @summary REST controller for notifications
 * @since 1.0
 * @author GeOps Labs
 */
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification management endpoints")
public class NotificationController {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationController(
        NotificationCommandService notificationCommandService,
        NotificationQueryService notificationQueryService
    ) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Create a new notification
     *
     * @param resource CreateNotificationResource
     * @return NotificationResource
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new notification")
    public ResponseEntity<NotificationResource> createNotification(@RequestBody CreateNotificationResource resource) {
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        var notificationOptional = notificationCommandService.handle(command);
        
        if (notificationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notificationOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResource);
    }

    /**
     * Get all notifications for a user
     *
     * @param userId User ID
     * @return List of NotificationResource
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<List<NotificationResource>> getNotificationsByUserId(@PathVariable Long userId) {
        var query = new GetNotificationsByUserIdQuery(userId);
        var notifications = notificationQueryService.handle(query);
        
        var resources = notifications.stream()
            .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
            .toList();
        
        return ResponseEntity.ok(resources);
    }

    /**
     * Get unread notification count for a user
     *
     * @param userId User ID
     * @return Unread count
     */
    @GetMapping("/user/{userId}/unread-count")
    @Operation(summary = "Get unread notification count for a user")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        var query = new GetUnreadCountByUserIdQuery(userId);
        var count = notificationQueryService.handle(query);
        return ResponseEntity.ok(count);
    }

    /**
     * Mark a notification as read
     *
     * @param id Notification ID
     * @return NotificationResource
     */
    @PutMapping("/{id}/mark-as-read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<NotificationResource> markAsRead(@PathVariable Long id) {
        var command = new MarkNotificationAsReadCommand(id);
        var notificationOptional = notificationCommandService.handle(command);
        
        if (notificationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notificationOptional.get());
        return ResponseEntity.ok(notificationResource);
    }

    /**
     * Mark all notifications as read for a user
     *
     * @param userId User ID
     * @return Number of notifications marked as read
     */
    @PutMapping("/user/{userId}/mark-all-as-read")
    @Operation(summary = "Mark all notifications as read for a user")
    public ResponseEntity<Integer> markAllAsRead(@PathVariable Long userId) {
        var count = notificationCommandService.markAllAsReadForUser(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * Delete a notification
     *
     * @param id Notification ID
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        var command = new DeleteNotificationCommand(id);
        var success = notificationCommandService.handle(command);
        
        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
