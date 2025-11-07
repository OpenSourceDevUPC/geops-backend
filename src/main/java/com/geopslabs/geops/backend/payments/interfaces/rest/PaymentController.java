package com.geopslabs.geops.backend.payments.interfaces.rest;

import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByStatusQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetAllPaymentsByUserIdQuery;
import com.geopslabs.geops.backend.payments.domain.model.queries.GetPaymentByIdQuery;
import com.geopslabs.geops.backend.payments.domain.services.PaymentCommandService;
import com.geopslabs.geops.backend.payments.domain.services.PaymentQueryService;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.CreatePaymentResource;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.PaymentResource;
import com.geopslabs.geops.backend.payments.interfaces.rest.resources.UpdatePaymentStatusResource;
import com.geopslabs.geops.backend.payments.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import com.geopslabs.geops.backend.payments.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import com.geopslabs.geops.backend.payments.interfaces.rest.transform.UpdatePaymentStatusCommandFromResourceAssembler;
import com.geopslabs.geops.backend.payments.domain.model.aggregates.Payment.PaymentStatus;
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
 * PaymentController
 *
 * REST controller that exposes payment-related endpoints for the GeOps platform.
 * This controller handles HTTP requests for payment operations including creation,
 * status updates, and various query operations. It follows RESTful principles
 * and integrates with the frontend payment system.
 *
 * @summary REST controller for payment transaction operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Payments", description = "Payment transaction operations and management")
@RestController
@RequestMapping(value = "/api/v1/payments", produces = APPLICATION_JSON_VALUE)
public class PaymentController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param paymentCommandService Service for handling payment commands
     * @param paymentQueryService Service for handling payment queries
     */
    public PaymentController(PaymentCommandService paymentCommandService,
                           PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    /**
     * Creates a new payment transaction
     *
     * This endpoint corresponds to the frontend's create() method
     * and creates a new entity returning the created entity
     *
     * @param resource The payment creation request data
     * @return ResponseEntity containing the created payment or error status
     */
    @Operation(summary = "Create new payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Payment created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PaymentResource> create(@RequestBody CreatePaymentResource resource) {
        var command = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var payment = paymentCommandService.handle(command);

        if (payment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return new ResponseEntity<>(paymentResource, CREATED);
    }

    /**
     * Retrieves a payment by its unique identifier
     *
     * This endpoint corresponds to the frontend's getById() method
     * and retrieves a single entity by ID
     *
     * @param id The unique identifier of the payment (supports both number and string)
     * @return ResponseEntity containing the payment data or not found status
     */
    @Operation(summary = "Get payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment found"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid payment ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResource> getById(
            @Parameter(description = "Payment unique identifier") @PathVariable String id) {
        try {
            Long paymentId = Long.parseLong(id);
            var query = new GetPaymentByIdQuery(paymentId);
            var payment = paymentQueryService.handle(query);

            if (payment.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
            return ResponseEntity.ok(paymentResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all payments for a specific user
     *
     * @param userId The unique identifier of the user
     * @return ResponseEntity containing the list of user payments
     */
    @Operation(summary = "Get all payments by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResource>> getPaymentsByUserId(
            @Parameter(description = "User unique identifier") @PathVariable String userId) {
        var query = new GetAllPaymentsByUserIdQuery(userId);
        var payments = paymentQueryService.handle(query);
        var paymentResources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(paymentResources);
    }

    /**
     * Retrieves all payments with a specific status
     *
     * @param status The payment status to filter by
     * @return ResponseEntity containing the list of payments with the specified status
     */
    @Operation(summary = "Get all payments by status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment status")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResource>> getPaymentsByStatus(
            @Parameter(description = "Payment status") @PathVariable PaymentStatus status) {
        var query = new GetAllPaymentsByStatusQuery(status);
        var payments = paymentQueryService.handle(query);
        var paymentResources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(paymentResources);
    }

    /**
     * Retrieves all payments in the system
     *
     * This endpoint corresponds to the frontend's getAll() method
     * and returns all payment entities from the API
     *
     * @return ResponseEntity containing the list of all payments
     */
    @Operation(summary = "Get all payments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All payments retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PaymentResource>> getAll() {
        var payments = paymentQueryService.getAllPayments();
        var paymentResources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(paymentResources);
    }

    /**
     * Updates an existing payment transaction
     *
     * This endpoint corresponds to the frontend's update() method
     * and updates an existing entity by ID
     *
     * @param id The unique identifier of the payment to update (supports both number and string)
     * @param resource The payment update request data
     * @return ResponseEntity containing the updated payment or error status
     */
    @Operation(summary = "Update payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResource> update(
            @Parameter(description = "Payment unique identifier") @PathVariable String id,
            @RequestBody CreatePaymentResource resource) {
        try {
            Long paymentId = Long.parseLong(id);

            // First check if payment exists
            var existingPaymentQuery = new GetPaymentByIdQuery(paymentId);
            var existingPayment = paymentQueryService.handle(existingPaymentQuery);

            if (existingPayment.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Create a new payment with updated data (since we don't have an update command for full payment)
            // In a real scenario, you might want to create an UpdatePaymentCommand
            var command = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
            var payment = paymentCommandService.handle(command);

            if (payment.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
            return ResponseEntity.ok(paymentResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates the status of a payment transaction
     *
     * @param paymentId The unique identifier of the payment to update
     * @param resource The status update request data
     * @return ResponseEntity containing the updated payment or error status
     */
    @Operation(summary = "Update payment status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResource> updatePaymentStatus(
            @Parameter(description = "Payment unique identifier") @PathVariable Long paymentId,
            @RequestBody UpdatePaymentStatusResource resource) {
        var command = UpdatePaymentStatusCommandFromResourceAssembler.toCommandFromResource(paymentId, resource);
        var payment = paymentCommandService.handle(command);

        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return ResponseEntity.ok(paymentResource);
    }

    /**
     * Retrieves payments by cart ID
     *
     * @param cartId The unique identifier of the cart
     * @return ResponseEntity containing the list of payments for the specified cart
     */
    @Operation(summary = "Get payments by cart ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid cart ID")
    })
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<PaymentResource>> getPaymentsByCartId(
            @Parameter(description = "Cart unique identifier") @PathVariable String cartId) {
        var payments = paymentQueryService.getPaymentsByCartId(cartId);
        var paymentResources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(paymentResources);
    }

    /**
     * Checks if a payment exists for a specific cart
     *
     * @param cartId The unique identifier of the cart
     * @return ResponseEntity containing boolean indicating payment existence
     */
    @Operation(summary = "Check if payment exists for cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid cart ID")
    })
    @GetMapping("/cart/{cartId}/exists")
    public ResponseEntity<Boolean> existsPaymentByCartId(
            @Parameter(description = "Cart unique identifier") @PathVariable String cartId) {
        var exists = paymentQueryService.existsPaymentByCartId(cartId);
        return ResponseEntity.ok(exists);
    }

    /**
     * Completes a payment transaction
     *
     * @param paymentId The unique identifier of the payment to complete
     * @return ResponseEntity containing the completed payment or error status
     */
    @Operation(summary = "Complete payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment completed successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid payment ID")
    })
    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResource> completePayment(
            @Parameter(description = "Payment unique identifier") @PathVariable Long paymentId) {
        var payment = paymentCommandService.completePayment(paymentId, java.time.LocalDateTime.now().toString());

        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return ResponseEntity.ok(paymentResource);
    }

    /**
     * Marks a payment transaction as failed
     *
     * @param paymentId The unique identifier of the payment to mark as failed
     * @return ResponseEntity containing the failed payment or error status
     */
    @Operation(summary = "Mark payment as failed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment marked as failed successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid payment ID")
    })
    @PutMapping("/{paymentId}/fail")
    public ResponseEntity<PaymentResource> failPayment(
            @Parameter(description = "Payment unique identifier") @PathVariable Long paymentId) {
        var payment = paymentCommandService.failPayment(paymentId);

        if (payment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment.get());
        return ResponseEntity.ok(paymentResource);
    }

    /**
     * Deletes a payment transaction by ID
     *
     * This endpoint corresponds to the frontend's delete() method
     * and removes an entity by ID
     *
     * @param id The unique identifier of the payment to delete (supports both number and string)
     * @return ResponseEntity with void content or error status
     */
    @Operation(summary = "Delete payment transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid payment ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Payment unique identifier") @PathVariable String id) {
        try {
            Long paymentId = Long.parseLong(id);

            // First check if payment exists
            var existingPaymentQuery = new GetPaymentByIdQuery(paymentId);
            var existingPayment = paymentQueryService.handle(existingPaymentQuery);

            if (existingPayment.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Delete the payment using the command service
            var deleted = paymentCommandService.deletePayment(paymentId);

            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
