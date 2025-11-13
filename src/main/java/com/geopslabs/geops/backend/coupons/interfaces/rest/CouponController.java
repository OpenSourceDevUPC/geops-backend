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
import com.geopslabs.geops.backend.offers.domain.services.OfferQueryService;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOffersByIdsQuery;
import com.geopslabs.geops.backend.offers.domain.model.aggregates.Offer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final OfferQueryService offerQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param couponCommandService Service for handling coupon commands
     * @param couponQueryService Service for handling coupon queries
     * @param offerQueryService Service for handling offer queries (used to embed offer data)
     */
    public CouponController(CouponCommandService couponCommandService,
                           CouponQueryService couponQueryService,
                           OfferQueryService offerQueryService) {
        this.couponCommandService = couponCommandService;
        this.couponQueryService = couponQueryService;
        this.offerQueryService = offerQueryService;
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

        var couponResource = mapWithOffer(coupon.get());
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
                    .map(this::mapWithOffer)
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
                .map(this::mapWithOffer)
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

            var couponResource = mapWithOffer(coupon.get());
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

        List<com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon> coupons;

        if (userId != null && !userId.isBlank()) {
            var query = new GetAllCouponsByUserIdQuery(userId);
            coupons = couponQueryService.handle(query);
        } else {
            coupons = couponQueryService.getAllCoupons();
        }

        // Batch fetch offers to avoid N+1 queries
        var offerMap = batchFetchOffersForCoupons(coupons);

        var couponResources = coupons.stream()
                .map(c -> mapWithOffer(c, offerMap.get(c.getOfferId())))
                .toList();

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

            var couponResource = mapWithOffer(coupon.get());
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

        var offerMap = batchFetchOffersForCoupons(coupons);
        var couponResources = coupons.stream()
                .map(c -> mapWithOffer(c, offerMap.get(c.getOfferId())))
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

        var offerMap = batchFetchOffersForCoupons(coupons);
        var couponResources = coupons.stream()
                .map(c -> mapWithOffer(c, offerMap.get(c.getOfferId())))
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

        var couponResource = mapWithOffer(coupon.get());
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

        var offerMap = batchFetchOffersForCoupons(coupons);
        var couponResources = coupons.stream()
                .map(c -> mapWithOffer(c, offerMap.get(c.getOfferId())))
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

        var offerMap = batchFetchOffersForCoupons(coupons);
        var couponResources = coupons.stream()
                .map(c -> mapWithOffer(c, offerMap.get(c.getOfferId())))
                .toList();

        return ResponseEntity.ok(couponResources);
    }

    /**
     * Helper: Maps a Coupon entity to a CouponResource and attempts to load the related Offer
     * when coupon.offerId is present. If fetching the Offer fails or is not present, the
     * returned resource will have a null offer field.
     *
     * @param coupon Coupon domain entity
     * @return CouponResource including optional embedded OfferResource
     */
    private CouponResource mapWithOffer(com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon coupon) {
        if (coupon.getOfferId() == null) {
            return CouponResourceFromEntityAssembler.toResourceFromEntity(coupon);
        }

        try {
            Optional<Offer> offerOpt = offerQueryService.handle(new GetOfferByIdQuery(coupon.getOfferId()));
            return CouponResourceFromEntityAssembler.toResourceFromEntityWithOffer(coupon, offerOpt.orElse(null));
        } catch (Exception e) {
            // If offer lookup fails, return coupon without embedded offer to avoid breaking client
            System.err.println("Failed to load offer for coupon " + coupon.getId() + ": " + e.getMessage());
            return CouponResourceFromEntityAssembler.toResourceFromEntity(coupon);
        }
    }

    /**
     * Overload that maps coupon using a pre-fetched Offer (may be null).
     * This helper is used by list endpoints after batch fetching offers.
     */
    private CouponResource mapWithOffer(com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon coupon, Offer offer) {
        if (coupon.getOfferId() == null) {
            return CouponResourceFromEntityAssembler.toResourceFromEntity(coupon);
        }
        return CouponResourceFromEntityAssembler.toResourceFromEntityWithOffer(coupon, offer);
    }

    /**
     * Batch fetch offers for a list of coupons and return a map offerId -> Offer.
     * If no offer ids are present or an error occurs, returns an empty map.
     */
    private Map<Long, Offer> batchFetchOffersForCoupons(List<com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon> coupons) {
        try {
            Set<Long> ids = coupons.stream()
                    .map(com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon::getOfferId)
                    .filter(id -> id != null && id > 0)
                    .collect(Collectors.toSet());

            if (ids.isEmpty()) {
                return Map.of();
            }

            var offers = offerQueryService.handle(new GetOffersByIdsQuery(ids.stream().toList()));
            return offers.stream().collect(Collectors.toMap(Offer::getId, o -> o));
        } catch (Exception e) {
            System.err.println("Failed to batch fetch offers: " + e.getMessage());
            return Map.of();
        }
    }
}
