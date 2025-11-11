package com.geopslabs.geops.backend.favorites.interfaces.rest;

import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoriteByUserIdAndOfferIdQuery;
import com.geopslabs.geops.backend.favorites.domain.model.queries.GetFavoritesByUserIdQuery;
import com.geopslabs.geops.backend.favorites.domain.services.FavoriteCommandService;
import com.geopslabs.geops.backend.favorites.domain.services.FavoriteQueryService;
import com.geopslabs.geops.backend.favorites.interfaces.rest.resources.CreateFavoriteResource;
import com.geopslabs.geops.backend.favorites.interfaces.rest.resources.FavoriteResource;
import com.geopslabs.geops.backend.favorites.interfaces.rest.transform.CreateFavoriteCommandFromResourceAssembler;
import com.geopslabs.geops.backend.favorites.interfaces.rest.transform.FavoriteResourceFromEntityAssembler;
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
 * FavoriteController
 *
 * REST controller for managing favorite offers for users
 * This controller provides endpoints to create, retrieve, and delete favorites
 *
 * @summary REST controller for favorite management
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Favorites", description = "Favorite operations and management")
@RestController
@RequestMapping(value = "/api/v1/favorites", produces = APPLICATION_JSON_VALUE)
public class FavoriteController {

    private final FavoriteCommandService favoriteCommandService;
    private final FavoriteQueryService favoriteQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param favoriteCommandService Service for handling favorite commands
     * @param favoriteQueryService Service for handling favorite queries
     */
    public FavoriteController(FavoriteCommandService favoriteCommandService,
                             FavoriteQueryService favoriteQueryService) {
        this.favoriteCommandService = favoriteCommandService;
        this.favoriteQueryService = favoriteQueryService;
    }

    /**
     * Creates a new favorite
     *
     * @param resource The favorite creation request data
     * @return ResponseEntity containing the created favorite or error status
     */
    @Operation(summary = "Create new favorite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Favorite created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data or favorite already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FavoriteResource> create(@RequestBody CreateFavoriteResource resource) {
        var command = CreateFavoriteCommandFromResourceAssembler.toCommandFromResource(resource);
        var favorite = favoriteCommandService.handle(command);

        if (favorite.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var favoriteResource = FavoriteResourceFromEntityAssembler.toResourceFromEntity(favorite.get());
        return new ResponseEntity<>(favoriteResource, CREATED);
    }

    /**
     * Retrieves favorites by userId or checks if a specific favorite exists
     *
     * @param userId The ID of the user whose favorites are to be retrieved (required)
     * @param offerId The ID of the offer to check if favorite (optional)
     * @return ResponseEntity containing the list of favorites or specific favorite, or error status
     */
    @Operation(summary = "Get favorites by userId or check if favorite exists")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favorites retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "userId is required")
    })
    @GetMapping
    public ResponseEntity<?> getFavorites(
            @Parameter(description = "User ID (required)")
            @RequestParam(required = true) String userId,
            @Parameter(description = "Offer ID (optional - for checking if favorited)")
            @RequestParam(required = false) String offerId) {

        if (offerId != null && !offerId.isBlank()) {
            // GET /favorites?userId=1&offerId=7
            // Check if specific favorite exists (for heart button)
            var query = new GetFavoriteByUserIdAndOfferIdQuery(userId, offerId);
            var favorite = favoriteQueryService.handle(query);

            if (favorite.isPresent()) {
                var favoriteResource = FavoriteResourceFromEntityAssembler
                    .toResourceFromEntity(favorite.get());
                return ResponseEntity.ok(favoriteResource);
            } else {
                // Return 200 with empty body to indicate "not favorited"
                return ResponseEntity.ok().build();
            }
        } else {
            // GET /favorites?userId=1
            // Get all favorites for user
            var query = new GetFavoritesByUserIdQuery(userId);
            var favorites = favoriteQueryService.handle(query);
            var favoriteResources = favorites.stream()
                    .map(FavoriteResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(favoriteResources);
        }
    }

    /**
     * Deletes a favorite by ID
     *
     * @param id The unique identifier of the favorite to delete
     * @return ResponseEntity with no content or error status
     */
    @Operation(summary = "Delete favorite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Favorite deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Favorite not found"),
        @ApiResponse(responseCode = "400", description = "Invalid favorite ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Favorite unique identifier") @PathVariable Long id) {

        boolean deleted = favoriteCommandService.handleDelete(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

