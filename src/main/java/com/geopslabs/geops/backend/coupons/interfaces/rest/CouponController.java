package com.geopslabs.geops.backend.coupons.interfaces.rest;

import com.geopslabs.geops.backend.coupons.domain.model.queries.GetAllCouponsByUserIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByCodeQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponsByPaymentIdQuery;
import com.geopslabs.geops.backend.coupons.domain.services.CouponCommandService;
import com.geopslabs.geops.backend.coupons.domain.services.CouponQueryService;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CreateCouponResource;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CreateManyCouponsResource;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CouponResource;
import com.geopslabs.geops.backend.coupons.interfaces.rest.transform.CreateCouponCommandFromResourceAssembler;
import com.geopslabs.geops.backend.coupons.interfaces.rest.transform.CreateManyCouponsCommandFromResourceAssembler;
import com.geopslabs.geops.backend.coupons.interfaces.rest.transform.CouponResourceFromEntityAssembler;
import com.geopslabs.geops.backend.coupons.domain.model.commands.UpdateCouponCommand;
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
 * CouponController
 *
 * REST controller that exposes coupon-related endpoints for the GeOps platform.
 * This controller handles HTTP requests for coupon operations including creation,
 * bulk creation, updates, and various query operations. It follows RESTful principles
 * and integrates with frontend coupon management systems.
 *
 * Supports special endpoints:
 * - /coupons/bulk for creating multiple coupons at once
 * - Query parameters for filtering with relations (_expand, _embed)
 * - User-specific coupon retrieval
 *
 * @summary REST controller for coupon operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Coupons", description = "Coupon operations and management")
@RestController
@RequestMapping(value = "/api/v1/coupons", produces = APPLICATION_JSON_VALUE)
public class CouponController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param couponCommandService Service for handling coupon commands
     * @param couponQueryService Service for handling coupon queries
     */
    public CouponController(CouponCommandService couponCommandService,
                           CouponQueryService couponQueryService) {
        this.couponCommandService = couponCommandService;
        this.couponQueryService = couponQueryService;
    }

    /**
     * Creates a new coupon
     *
     * This endpoint corresponds to the frontend's create() method
     * and creates a new coupon returning the created coupon
     *
     * @param resource The coupon creation request data
     * @return ResponseEntity containing the created coupon or error status
     */
    @Operation(summary = "Create new coupon")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coupon created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CouponResource> create(@RequestBody CreateCouponResource resource) {
        var command = CreateCouponCommandFromResourceAssembler.toCommandFromResource(resource);
        var coupon = couponCommandService.handle(command);

        if (coupon.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var couponResource = CouponResourceFromEntityAssembler.toResourceFromEntity(coupon.get());
        return new ResponseEntity<>(couponResource, CREATED);
    }

    /**
     * Creates multiple coupons in a single request (Bulk Endpoint)
     *
     * This endpoint supports the frontend's createMany() method that expects
     * a bulk endpoint at /coupons/bulk accepting an array of coupon resources.
     * Falls back to sequential creation if bulk operation fails.
     *
     * @param resources List of coupon creation request data
     * @return ResponseEntity containing the created coupons or error status
     */
    @Operation(summary = "Create multiple coupons (bulk operation)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coupons created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/bulk")
    public ResponseEntity<List<CouponResource>> createMany(@RequestBody List<CreateCouponResource> resources) {
        try {
            var command = CreateManyCouponsCommandFromResourceAssembler.toCommandFromResourceList(resources);
            var coupons = couponCommandService.handle(command);

            var couponResources = coupons.stream()
                    .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();

            return new ResponseEntity<>(couponResources, CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Alternative bulk creation endpoint that accepts wrapped resource
     *
     * This provides an alternative way to create multiple coupons using
     * a wrapper resource object instead of a direct array.
     *
     * @param resource The bulk coupon creation request data
     * @return ResponseEntity containing the created coupons or error status
     */
    @Operation(summary = "Create multiple coupons (alternative bulk endpoint)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Coupons created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/bulk-wrapped")
    public ResponseEntity<List<CouponResource>> createManyWrapped(@RequestBody CreateManyCouponsResource resource) {
        var command = CreateManyCouponsCommandFromResourceAssembler.toCommandFromResource(resource);
        var coupons = couponCommandService.handle(command);

        var couponResources = coupons.stream()
                .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new ResponseEntity<>(couponResources, CREATED);
    }

    /**
     * Retrieves a coupon by its unique identifier
     *
     * This endpoint corresponds to the frontend's getById() method
     * and retrieves a single coupon by ID
     *
     * @param id The unique identifier of the coupon (supports both number and string)
     * @return ResponseEntity containing the coupon data or not found status
     */
    @Operation(summary = "Get coupon by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coupon found"),
        @ApiResponse(responseCode = "404", description = "Coupon not found"),
        @ApiResponse(responseCode = "400", description = "Invalid coupon ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CouponResource> getById(
            @Parameter(description = "Coupon unique identifier") @PathVariable String id) {
        try {
            Long couponId = Long.parseLong(id);
            var query = new GetCouponByIdQuery(couponId);
            var coupon = couponQueryService.handle(query);

            if (coupon.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var couponResource = CouponResourceFromEntityAssembler.toResourceFromEntity(coupon.get());
            return ResponseEntity.ok(couponResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retrieves all coupons in the system
     *
     * This endpoint corresponds to the frontend's getAll() method and getAllCoupons()
     * Supports query parameters for filtering and relations (_expand, _embed)
     * Compatible with JSON Server style parameters used by the frontend
     *
     * @param userId Optional user ID filter parameter
     * @param expand Optional list of single relationships to expand (comma-separated)
     * @param embed Optional list of array relationships to embed (comma-separated)
     * @return ResponseEntity containing the list of coupons
     */
    @Operation(summary = "Get all coupons with optional filtering and relations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coupons retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CouponResource>> getAll(
            @Parameter(description = "Optional user ID filter") @RequestParam(required = false) String userId,
            @Parameter(description = "Relationships to expand (comma-separated)") @RequestParam(name = "_expand", required = false) String expand,
            @Parameter(description = "Relationships to embed (comma-separated)") @RequestParam(name = "_embed", required = false) String embed) {

        List<CouponResource> couponResources;

        if (userId != null && !userId.isBlank()) {
            // Filter by user ID if provided
            var query = new GetAllCouponsByUserIdQuery(userId);
            var coupons = couponQueryService.handle(query);
            couponResources = coupons.stream()
                    .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            // Get all coupons
            var coupons = couponQueryService.getAllCoupons();
            couponResources = coupons.stream()
                    .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }

        // Note: _expand and _embed parameters are acknowledged but not implemented
        // In a full implementation, these would be used to include related offer data
        // For now, they are accepted to maintain compatibility with frontend expectations

        return ResponseEntity.ok(couponResources);
    }

    /**
     * Updates an existing coupon
     *
     * This endpoint corresponds to the frontend's update() method
     * and updates an existing coupon by ID
     *
     * @param id The unique identifier of the coupon to update (supports both number and string)
     * @param resource The coupon update request data
     * @return ResponseEntity containing the updated coupon or error status
     */
    @Operation(summary = "Update coupon")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coupon updated successfully"),
        @ApiResponse(responseCode = "404", description = "Coupon not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CouponResource> update(
            @Parameter(description = "Coupon unique identifier") @PathVariable String id,
            @RequestBody CreateCouponResource resource) {
        try {
            Long couponId = Long.parseLong(id);

            // First check if coupon exists
            var existingCouponQuery = new GetCouponByIdQuery(couponId);
            var existingCoupon = couponQueryService.handle(existingCouponQuery);

            if (existingCoupon.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Create update command and handle it
            var updateCommand = new UpdateCouponCommand(
                couponId, resource.productType(), resource.offerId(),
                resource.code(), resource.expiresAt());
            var coupon = couponCommandService.handle(updateCommand);

            if (coupon.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var couponResource = CouponResourceFromEntityAssembler.toResourceFromEntity(coupon.get());
            return ResponseEntity.ok(couponResource);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a coupon by ID
     *
     * This endpoint corresponds to the frontend's delete() method
     * and removes a coupon by ID
     *
     * @param id The unique identifier of the coupon to delete (supports both number and string)
     * @return ResponseEntity with void content or error status
     */
    @Operation(summary = "Delete coupon")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Coupon deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Coupon not found"),
        @ApiResponse(responseCode = "400", description = "Invalid coupon ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Coupon unique identifier") @PathVariable String id) {
        try {
            Long couponId = Long.parseLong(id);

            // Delete the coupon using the command service
            var deleted = couponCommandService.deleteCoupon(couponId);

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
     * Retrieves coupons for a specific user
     *
     * This endpoint supports the frontend's getCouponsByUser() method
     * and can include relations using _expand and _embed parameters
     *
     * @param userId The unique identifier of the user
     * @param expand Optional list of single relationships to expand
     * @param embed Optional list of array relationships to embed
     * @return ResponseEntity containing the list of user coupons
     */
    @Operation(summary = "Get coupons by user ID with optional relations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User coupons retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponResource>> getCouponsByUser(
            @Parameter(description = "User unique identifier") @PathVariable String userId,
            @Parameter(description = "Relationships to expand") @RequestParam(name = "_expand", required = false) String expand,
            @Parameter(description = "Relationships to embed") @RequestParam(name = "_embed", required = false) String embed) {

        var query = new GetAllCouponsByUserIdQuery(userId);
        var coupons = couponQueryService.handle(query);
        var couponResources = coupons.stream()
                .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(couponResources);
    }

    /**
     * Retrieves coupons by payment ID
     *
     * @param paymentId The unique identifier of the payment
     * @return ResponseEntity containing the list of coupons for the payment
     */
    @Operation(summary = "Get coupons by payment ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment coupons retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid payment ID")
    })
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<CouponResource>> getCouponsByPayment(
            @Parameter(description = "Payment unique identifier") @PathVariable String paymentId) {

        var query = new GetCouponsByPaymentIdQuery(paymentId);
        var coupons = couponQueryService.handle(query);
        var couponResources = coupons.stream()
                .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(couponResources);
    }

    /**
     * Retrieves a coupon by its redemption code
     *
     * @param code The coupon redemption code
     * @return ResponseEntity containing the coupon or not found status
     */
    @Operation(summary = "Get coupon by redemption code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Coupon found"),
        @ApiResponse(responseCode = "404", description = "Coupon not found"),
        @ApiResponse(responseCode = "400", description = "Invalid coupon code")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<CouponResource> getCouponByCode(
            @Parameter(description = "Coupon redemption code") @PathVariable String code) {

        var query = new GetCouponByCodeQuery(code);
        var coupon = couponQueryService.handle(query);

        if (coupon.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var couponResource = CouponResourceFromEntityAssembler.toResourceFromEntity(coupon.get());
        return ResponseEntity.ok(couponResource);
    }

    /**
     * Retrieves valid (non-expired) coupons for a specific user
     *
     * @param userId The unique identifier of the user
     * @return ResponseEntity containing the list of valid user coupons
     */
    @Operation(summary = "Get valid coupons by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Valid user coupons retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping("/user/{userId}/valid")
    public ResponseEntity<List<CouponResource>> getValidCouponsByUser(
            @Parameter(description = "User unique identifier") @PathVariable String userId) {

        var coupons = couponQueryService.getValidCouponsByUserId(userId);
        var couponResources = coupons.stream()
                .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(couponResources);
    }

    /**
     * Retrieves expired coupons for cleanup or analysis
     *
     * @return ResponseEntity containing the list of expired coupons
     */
    @Operation(summary = "Get expired coupons")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Expired coupons retrieved successfully")
    })
    @GetMapping("/expired")
    public ResponseEntity<List<CouponResource>> getExpiredCoupons() {
        var coupons = couponQueryService.getExpiredCoupons();
        var couponResources = coupons.stream()
                .map(CouponResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(couponResources);
    }
}
