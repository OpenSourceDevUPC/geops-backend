package com.geopslabs.geops.backend.offers.interfaces.rest;

import com.geopslabs.geops.backend.offers.domain.model.commands.DeleteOfferCommand;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersByCampaignIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetAllOffersQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOfferByIdQuery;
import com.geopslabs.geops.backend.offers.domain.model.queries.GetOffersByIdsQuery;
import com.geopslabs.geops.backend.offers.domain.services.OfferCommandService;
import com.geopslabs.geops.backend.offers.domain.services.OfferQueryService;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.CreateOfferResource;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.OfferResource;
import com.geopslabs.geops.backend.offers.interfaces.rest.resources.UpdateOfferResource;
import com.geopslabs.geops.backend.offers.interfaces.rest.transform.CreateOfferCommandFromResourceAssembler;
import com.geopslabs.geops.backend.offers.interfaces.rest.transform.OfferResourceFromEntityAssembler;
import com.geopslabs.geops.backend.offers.interfaces.rest.transform.UpdateOfferCommandFromResourceAssembler;
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
 * OfferController
 * REST controller that exposes offer endpoints for the GeOps platform
 * This controller handles HTTP requests for offer CRUD operations
 *
 * @summary REST controller for offer operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Offers", description = "Offer operations and management")
@RestController
@RequestMapping(value = "/api/v1/offers", produces = APPLICATION_JSON_VALUE)
public class OfferController {

    private final OfferCommandService offerCommandService;
    private final OfferQueryService offerQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param offerCommandService Service for handling offer commands
     * @param offerQueryService Service for handling offer queries
     */
    public OfferController(OfferCommandService offerCommandService,
                          OfferQueryService offerQueryService) {
        this.offerCommandService = offerCommandService;
        this.offerQueryService = offerQueryService;
    }

    /**
     * Creates a new offer
     *
     * @param resource The offer creation request data
     * @return ResponseEntity containing the created offer or error status
     */
    @Operation(summary = "Create new offer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Offer created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<OfferResource> create(@RequestBody CreateOfferResource resource) {
        var command = CreateOfferCommandFromResourceAssembler.toCommandFromResource(resource);
        var offer = offerCommandService.handle(command);

        if (offer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var offerResource = OfferResourceFromEntityAssembler.toResourceFromEntity(offer.get());
        return new ResponseEntity<>(offerResource, CREATED);
    }

    /**
     * Retrieves an offer by its unique identifier
     *
     * @param id The unique identifier of the offer
     * @return ResponseEntity containing the offer data or not found status
     */
    @Operation(summary = "Get offer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Offer found"),
        @ApiResponse(responseCode = "404", description = "Offer not found"),
        @ApiResponse(responseCode = "400", description = "Invalid offer ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OfferResource> getById(
            @Parameter(description = "Offer unique identifier") @PathVariable Long id) {
        var query = new GetOfferByIdQuery(id);
        var offer = offerQueryService.handle(query);

        if (offer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var offerResource = OfferResourceFromEntityAssembler.toResourceFromEntity(offer.get());
        return ResponseEntity.ok(offerResource);
    }

    /**
     * Retrieves all offers or offers by a list of IDs
     * This endpoint supports two modes:
     * 1. GET /offers - retrieves all offers
     * 2. GET /offers?id=1&id=2&id=3 - retrieves offers by specified IDs
     *
     * @param id Optional list of offer IDs to filter by
     * @return ResponseEntity containing the list of offers
     */
    @Operation(summary = "Get all offers or offers by IDs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Offers retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<OfferResource>> getOffers(
            @Parameter(description = "Optional list of offer IDs") @RequestParam(required = false) List<Long> id) {

        List<OfferResource> offerResources;

        if (id != null && !id.isEmpty()) {
            // GET /offers?id=1&id=2&id=3
            var query = new GetOffersByIdsQuery(id);
            var offers = offerQueryService.handle(query);
            offerResources = offers.stream()
                    .map(OfferResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            // GET /offers
            var query = new GetAllOffersQuery();
            var offers = offerQueryService.handle(query);
            offerResources = offers.stream()
                    .map(OfferResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }

        return ResponseEntity.ok(offerResources);
    }

    /**
     * Updates an existing offer
     *
     * @param id The unique identifier of the offer to update
     * @param resource The offer update request data
     * @return ResponseEntity containing the updated offer or error status
     */
    @Operation(summary = "Update offer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Offer updated successfully"),
        @ApiResponse(responseCode = "404", description = "Offer not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OfferResource> update(
            @Parameter(description = "Offer unique identifier") @PathVariable Long id,
            @RequestBody UpdateOfferResource resource) {

        // First check if offer exists
        var existingOfferQuery = new GetOfferByIdQuery(id);
        var existingOffer = offerQueryService.handle(existingOfferQuery);

        if (existingOffer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Create update command and handle it
        var updateCommand = UpdateOfferCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var offer = offerCommandService.handle(updateCommand);

        if (offer.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var offerResource = OfferResourceFromEntityAssembler.toResourceFromEntity(offer.get());
        return ResponseEntity.ok(offerResource);
    }

    /**
     * Deletes an offer by ID
     *
     * @param id The unique identifier of the offer to delete
     * @return ResponseEntity with no content or error status
     */
    @Operation(summary = "Delete offer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Offer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Offer not found"),
        @ApiResponse(responseCode = "400", description = "Invalid offer ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Offer unique identifier") @PathVariable Long id) {

        boolean deleted = offerCommandService.handle(new DeleteOfferCommand(id));

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * Gets all offers from a campaign.
     * @param id The campaign id.
     * @return A list of {@link OfferResource} that has a campaign in common.
     */
    @Operation(summary = "Get all offers from a campaign", description = "Get all offers by using a campaign unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Offers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Offers not found")
    })
    @GetMapping("/campaign/{id}")
    public ResponseEntity<List<OfferResource>> getByCampaignId(@PathVariable Long id){
        var query = new GetAllOffersByCampaignIdQuery(id);

        var offers = offerQueryService.handle(query);
        if(offers.isEmpty()) return ResponseEntity.notFound().build();
        var offersResource =  offers.stream()
                .map(OfferResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(offersResource);
    }
}
