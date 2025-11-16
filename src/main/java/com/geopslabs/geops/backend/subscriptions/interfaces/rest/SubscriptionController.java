package com.geopslabs.geops.backend.subscriptions.interfaces.rest;

import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetAllSubscriptionsByTypeQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.geopslabs.geops.backend.subscriptions.domain.model.queries.GetRecommendedSubscriptionsQuery;
import com.geopslabs.geops.backend.subscriptions.domain.services.SubscriptionCommandService;
import com.geopslabs.geops.backend.subscriptions.domain.services.SubscriptionQueryService;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources.SubscriptionResource;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.geopslabs.geops.backend.subscriptions.interfaces.rest.resources.UpdateSubscriptionResource;
import com.geopslabs.geops.backend.subscriptions.domain.model.aggregates.Subscription.SubscriptionType;
import com.geopslabs.geops.backend.subscriptions.domain.model.commands.UpdateSubscriptionCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * SubscriptionController
 *
 * REST controller that exposes subscription plan endpoints for the GeOps platform.
 * This controller handles HTTP requests for subscription CRUD operations matching
 * the frontend interface structure: { id, price, recommended, type }
 *
 * @summary REST controller for subscription plan operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Subscriptions", description = "Subscription plan operations and management")
@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param subscriptionCommandService Service for handling subscription commands
     * @param subscriptionQueryService Service for handling subscription queries
     */
    public SubscriptionController(SubscriptionCommandService subscriptionCommandService,
                                SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    /**
     * Creates a new subscription plan
     *
     * This endpoint corresponds to the frontend's create() method
     * and creates a new subscription plan returning the created plan
     *
     * @param resource The subscription plan creation request data
     * @return ResponseEntity containing the created subscription plan or error status
     */
    @Operation(summary = "Create new subscription plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Subscription plan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<SubscriptionResource> create(@RequestBody CreateSubscriptionResource resource) {
        var command = CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var subscription = subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
        return new ResponseEntity<>(subscriptionResource, CREATED);
    }

    /**
     * Retrieves a subscription plan by its unique identifier
     *
     * This endpoint corresponds to the frontend's getById() method
     * and retrieves a single subscription plan by ID
     *
     * @param id The unique identifier of the subscription plan (supports both number and string)
     * @return ResponseEntity containing the subscription plan data or not found status
     */
    @Operation(summary = "Get subscription plan by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription plan found"),
        @ApiResponse(responseCode = "404", description = "Subscription plan not found"),
        @ApiResponse(responseCode = "400", description = "Invalid subscription ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResource> getById(
            @Parameter(description = "Subscription unique identifier") @PathVariable String id) {
        try {
            Long subscriptionId = Long.parseLong(id);
            var query = new GetSubscriptionByIdQuery(subscriptionId);
            var subscription = subscriptionQueryService.handle(query);

            if (subscription.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.ok(subscriptionResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all subscription plans in the system
     *
     * This endpoint corresponds to the frontend's getAll() method
     * and returns all subscription plans from the API
     *
     * @return ResponseEntity containing the list of all subscription plans
     */
    @Operation(summary = "Get all subscription plans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All subscription plans retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<SubscriptionResource>> getAll() {
        var subscriptions = subscriptionQueryService.getAllSubscriptions();
        var subscriptionResources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(subscriptionResources);
    }

    /**
     * Updates an existing subscription plan
     *
     * This endpoint corresponds to the frontend's update() method
     * and updates an existing subscription plan by ID
     *
     * @param id The unique identifier of the subscription plan to update (supports both number and string)
     * @param resource The subscription plan update request data
     * @return ResponseEntity containing the updated subscription plan or error status
     */
    @Operation(summary = "Update subscription plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription plan updated successfully"),
        @ApiResponse(responseCode = "404", description = "Subscription plan not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResource> update(
            @Parameter(description = "Subscription unique identifier") @PathVariable String id,
            @RequestBody UpdateSubscriptionResource resource) {
        try {
            Long subscriptionId = Long.parseLong(id);

            // First check if subscription exists
            var existingSubscriptionQuery = new GetSubscriptionByIdQuery(subscriptionId);
            var existingSubscription = subscriptionQueryService.handle(existingSubscriptionQuery);

            if (existingSubscription.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Create update command and handle it
            var updateCommand = new UpdateSubscriptionCommand(
                subscriptionId,
                resource.price(),
                resource.recommended(),
                resource.type());
            var subscription = subscriptionCommandService.handle(updateCommand);

            if (subscription.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription.get());
            return ResponseEntity.ok(subscriptionResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a subscription plan by ID
     *
     * This endpoint corresponds to the frontend's delete() method
     * and removes a subscription plan by ID
     *
     * @param id The unique identifier of the subscription plan to delete (supports both number and string)
     * @return ResponseEntity with void content or error status
     */
    @Operation(summary = "Delete subscription plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Subscription plan deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Subscription plan not found"),
        @ApiResponse(responseCode = "400", description = "Invalid subscription ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Subscription unique identifier") @PathVariable String id) {
        try {
            Long subscriptionId = Long.parseLong(id);

            // Delete the subscription using the command service
            var deleted = subscriptionCommandService.deleteSubscription(subscriptionId);

            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all subscription plans with a specific type
     *
     * @param type The subscription type to filter by (BASIC or PREMIUM)
     * @return ResponseEntity containing the list of subscription plans with the specified type
     */
    @Operation(summary = "Get all subscription plans by type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription plans retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid subscription type")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<SubscriptionResource>> getSubscriptionsByType(
            @Parameter(description = "Subscription type") @PathVariable SubscriptionType type) {
        var query = new GetAllSubscriptionsByTypeQuery(type);
        var subscriptions = subscriptionQueryService.handle(query);
        var subscriptionResources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(subscriptionResources);
    }

    /**
     * Retrieves all recommended subscription plans
     *
     * @return ResponseEntity containing the list of recommended subscription plans
     */
    @Operation(summary = "Get recommended subscription plans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recommended subscription plans retrieved successfully")
    })
    @GetMapping("/recommended")
    public ResponseEntity<List<SubscriptionResource>> getRecommendedSubscriptions() {
        var query = new GetRecommendedSubscriptionsQuery();
        var subscriptions = subscriptionQueryService.handle(query);
        var subscriptionResources = subscriptions.stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(subscriptionResources);
    }
}
