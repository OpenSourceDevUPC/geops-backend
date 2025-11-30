package com.geopslabs.geops.backend.campaign.interfaces.rest;

import com.geopslabs.geops.backend.campaign.domain.model.commands.DeleteCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsByUserIdQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetAllCampaignsQuery;
import com.geopslabs.geops.backend.campaign.domain.model.queries.GetCampaignByIdQuery;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignCommandService;
import com.geopslabs.geops.backend.campaign.domain.services.CampaignQueryService;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.CampaignResource;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.CreateCampaignResource;
import com.geopslabs.geops.backend.campaign.interfaces.rest.resources.UpdateCampaignResource;
import com.geopslabs.geops.backend.campaign.interfaces.rest.transform.CampaignResourceFromEntityAssembler;
import com.geopslabs.geops.backend.campaign.interfaces.rest.transform.CreateCampaignCommandFromResourceAssembler;
import com.geopslabs.geops.backend.campaign.interfaces.rest.transform.UpdateCampaignCommandFromResourceAssembler;
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

@Tag(name = "Campaigns", description = "Campaign operations and management")
@RestController
@RequestMapping(value = "/api/v1/campaigns", produces = APPLICATION_JSON_VALUE)
public class CampaignController {
    private final CampaignCommandService campaignCommandService;
    private final CampaignQueryService campaignQueryService;

    public CampaignController(CampaignCommandService campaignCommandService, CampaignQueryService campaignQueryService) {
        this.campaignCommandService = campaignCommandService;
        this.campaignQueryService = campaignQueryService;
    }

    @PostMapping()
    @Operation(summary = "Creates a new campaign", description = "Creates a Campaign with the given information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Campaign created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<CampaignResource> create(@RequestBody CreateCampaignResource resource) {
        var command = CreateCampaignCommandFromResourceAssembler.toCommandFromResource(resource);
        var createdCampaign = campaignCommandService.handle(command);
        if(createdCampaign.isEmpty()) return ResponseEntity.badRequest().build();
        var campaignResource = CampaignResourceFromEntityAssembler.toResourceFromEntity(createdCampaign.get());
        return new ResponseEntity<>(campaignResource,CREATED);
    }

    @GetMapping
    @Operation(summary = "Gets all the registered campaigns", description = "Gets all the campaigns in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campaigns retrieved successfully")
    })
    public ResponseEntity<List<CampaignResource>> getAll() {
        var campaigns = campaignQueryService.handle(new GetAllCampaignsQuery());
        var campaignResources = campaigns.stream()
                .map(CampaignResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(campaignResources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets a Campaign by its id", description = "Gets a campaign by giving its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campaign found"),
            @ApiResponse(responseCode = "404", description = "Campaign not found"),
            @ApiResponse(responseCode = "400", description = "Invalid campaign ID")
    })
    public ResponseEntity<CampaignResource> getById(
            @Parameter(description = "Review unique identifier") @PathVariable Long id) {
        var campaign = campaignQueryService.handle(new GetCampaignByIdQuery(id));
        if (campaign.isEmpty()) return ResponseEntity.notFound().build();
        var campaignResource = CampaignResourceFromEntityAssembler.toResourceFromEntity(campaign.get());
        return ResponseEntity.ok(campaignResource);
    }

    @GetMapping("/user/{userId}/campaigns")
    @Operation(summary = "Gets all the campaigns registered with the user",
            description = "Gets all campaigns from the user by giving the user unique identifier")
    public ResponseEntity<List<CampaignResource>> getCampaignsByUserId(
            @Parameter(description = "User unique identifier") @PathVariable Long userId)
    {
        var campaigns = campaignQueryService.handle(new GetAllCampaignsByUserIdQuery(userId));
        var campaignResources = campaigns.stream()
                .map(CampaignResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(campaignResources);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates a campaign",description = "Updates a campaign with its id and some given information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campaign successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or validation error"),
            @ApiResponse(responseCode = "404", description = "Campaign not found"),
    })
    public ResponseEntity<CampaignResource> update(@Parameter(description = "Review unique identifier")
                                                   @PathVariable Long id,
                                                   @RequestBody UpdateCampaignResource resource) {
        var existingCampaign = campaignQueryService.handle(new GetCampaignByIdQuery(id));
        if(existingCampaign.isEmpty()) return ResponseEntity.notFound().build();

        var updateCampaignCommand = UpdateCampaignCommandFromResourceAssembler.
                toCommandFromResource(id, resource);
        var updatedCampaign = campaignCommandService.handle(updateCampaignCommand);
        if(updatedCampaign.isEmpty()) return ResponseEntity.badRequest().build();

        var campaignResource = CampaignResourceFromEntityAssembler.toResourceFromEntity(updatedCampaign.get());
        return ResponseEntity.ok(campaignResource);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete campaign by id", description = "Delete a campaign by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Campaign deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    public ResponseEntity<Void> deleteById(@Parameter(description = "Campaign ID") @PathVariable Long id) {
        var command = new DeleteCampaignCommand(id);
        boolean deleted = campaignCommandService.handle(command);
        if(!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
