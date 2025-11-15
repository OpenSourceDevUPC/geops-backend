package com.geopslabs.geops.backend.identity.interfaces.rest;

import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.backend.identity.domain.services.UserQueryService;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UserResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * UserController
 *
 * REST controller that exposes user endpoints for the GeOps platform
 * This controller handles HTTP requests for user query operations
 *
 * @summary REST controller for user operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Users", description = "User identity operations and management")
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserQueryService userQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param userQueryService Service for handling user queries
     */
    public UserController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Retrieves a user by their unique identifier
     *
     * @param id The unique identifier of the user
     * @return ResponseEntity containing the user data or not found status
     */
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getById(
            @Parameter(description = "User unique identifier") @PathVariable Long id) {
        var query = new GetUserByIdQuery(id);
        var user = userQueryService.handle(query);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Retrieves the currently authenticated user's information
     *
     * Note: This is a simplified implementation. In a production environment,
     * you would extract the user's email from the security context/JWT token
     *
     * For demonstration purposes, this expects an email query parameter or header
     *
     * @param email The email of the authenticated user (should come from security context)
     * @return ResponseEntity containing the current user's data or not found status
     */
    @Operation(summary = "Get current authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResource> getMe(
            @Parameter(description = "Email of authenticated user")
            @RequestParam(required = false) String email) {

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetUserByEmailQuery(email);
        var user = userQueryService.handle(query);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}

