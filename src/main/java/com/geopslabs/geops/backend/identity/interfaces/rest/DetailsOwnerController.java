package com.geopslabs.geops.backend.identity.interfaces.rest;

import com.geopslabs.geops.backend.identity.domain.model.commands.CreateDetailsOwnerCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateDetailsOwnerCommand;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetDetailsOwnerByUserIdQuery;
import com.geopslabs.geops.backend.identity.domain.services.DetailsOwnerCommandService;
import com.geopslabs.geops.backend.identity.domain.services.DetailsOwnerQueryService;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.CreateDetailsOwnerResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.DetailsOwnerResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.transform.DetailsOwnerResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * DetailsOwnerController
 *
 * REST controller that exposes owner details endpoints for the GeOps platform
 * This controller handles HTTP requests for owner details operations
 *
 * @summary REST controller for owner details operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Owner Details", description = "Owner/business profile details operations and management")
@RestController
@RequestMapping(value = "/api/v1/users/{userId}/owner-details", produces = APPLICATION_JSON_VALUE)
public class DetailsOwnerController {

    private final DetailsOwnerQueryService detailsOwnerQueryService;
    private final DetailsOwnerCommandService detailsOwnerCommandService;

    /**
     * Constructor for dependency injection
     *
     * @param detailsOwnerQueryService Service for handling owner details queries
     * @param detailsOwnerCommandService Service for handling owner details commands
     */
    public DetailsOwnerController(DetailsOwnerQueryService detailsOwnerQueryService,
                                 DetailsOwnerCommandService detailsOwnerCommandService) {
        this.detailsOwnerQueryService = detailsOwnerQueryService;
        this.detailsOwnerCommandService = detailsOwnerCommandService;
    }

    /**
     * Retrieves owner details for a specific user
     *
     * @param userId The unique identifier of the user
     * @return ResponseEntity containing the owner details or not found status
     */
    @Operation(summary = "Get owner details by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Owner details found"),
        @ApiResponse(responseCode = "404", description = "Owner details not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping
    public ResponseEntity<DetailsOwnerResource> getByUserId(
            @Parameter(description = "User unique identifier") @PathVariable Long userId) {
        var query = new GetDetailsOwnerByUserIdQuery(userId);
        var detailsOwner = detailsOwnerQueryService.handle(query);

        if (detailsOwner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = DetailsOwnerResourceFromEntityAssembler.toResourceFromEntity(detailsOwner.get());
        return ResponseEntity.ok(resource);
    }

    /**
     * Creates owner details for a user
     *
     * Receives the owner details data in the request body and delegates
     * the creation operation to the command service. Returns the created resource.
     *
     * @param userId The id of the user
     * @param resource The owner details data to create (in request body)
     * @return ResponseEntity with created owner details or error status
     */
    @Operation(summary = "Create owner details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Owner details created"),
        @ApiResponse(responseCode = "400", description = "Invalid input or owner details already exist"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsOwnerResource> createOwnerDetails(
            @Parameter(description = "User unique identifier") @PathVariable Long userId,
            @RequestBody CreateDetailsOwnerResource resource) {

        var command = new CreateDetailsOwnerCommand(
            userId,
            resource.businessName(),
            resource.businessType(),
            resource.taxId(),
            resource.website(),
            resource.description(),
            resource.address(),
            resource.horarioAtencion()
        );

        var createdOpt = detailsOwnerCommandService.handle(command);
        if (createdOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var saved = createdOpt.get();
        var responseResource = DetailsOwnerResourceFromEntityAssembler.toResourceFromEntity(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseResource);
    }

    /**
     * Updates owner details for a user
     *
     * Receives the updated owner details data in the request body and delegates
     * the update operation to the command service. Returns the updated resource.
     *
     * @param userId The id of the user
     * @param resource The owner details data to update (in request body)
     * @return ResponseEntity with updated owner details or not found
     */
    @Operation(summary = "Update owner details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Owner details updated"),
        @ApiResponse(responseCode = "404", description = "Owner details not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailsOwnerResource> updateOwnerDetails(
            @Parameter(description = "User unique identifier") @PathVariable Long userId,
            @RequestBody CreateDetailsOwnerResource resource) {

        var command = new UpdateDetailsOwnerCommand(
            userId,
            resource.businessName(),
            resource.businessType(),
            resource.taxId(),
            resource.website(),
            resource.description(),
            resource.address(),
            resource.horarioAtencion()
        );

        var updatedOpt = detailsOwnerCommandService.handle(command);
        if (updatedOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var saved = updatedOpt.get();
        var responseResource = DetailsOwnerResourceFromEntityAssembler.toResourceFromEntity(saved);
        return ResponseEntity.ok(responseResource);
    }
}

