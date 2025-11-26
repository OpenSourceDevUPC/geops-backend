package com.geopslabs.geops.backend.identity.interfaces.rest;

import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateUserCommand;
import com.geopslabs.geops.backend.identity.domain.services.UserQueryService;
import com.geopslabs.geops.backend.identity.domain.services.UserCommandService;
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
    private final UserCommandService userCommandService;

    /**
     * Constructor for dependency injection
     *
     * @param userQueryService Service for handling user queries
     * @param userCommandService Service for handling user commands (create/update/delete)
     */
    public UserController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
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

    /**
     * Update an existing user
     *
     * Receives the updated user representation in the request body and delegates
     * the update operation to the command service. Returns the updated resource.
     *
     * The controller maps the incoming `UserResource` to an `UpdateUserCommand`
     * which is the expected input of the `UserCommandService`.
     *
     * @param id The id of the user to update
     * @param userResource The user data to update (in request body)
     * @return ResponseEntity with updated user or not found
     */
    @Operation(summary = "Update user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(
            @Parameter(description = "User unique identifier") @PathVariable Long id,
            @RequestBody UserResource userResource) {

        var query = new GetUserByIdQuery(id);
        var existing = userQueryService.handle(query);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Validate role if provided
        String role = userResource.role();
        if (role != null && !role.isBlank() && !role.equals("CONSUMER") && !role.equals("OWNER")) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validate plan if provided
        String plan = userResource.plan();
        if (plan != null && !plan.isBlank() && !plan.equals("BASIC") && !plan.equals("PREMIUM")) {
            return ResponseEntity.badRequest().build();
        }

        // Map incoming resource to domain command and delegate to service
        var cmd = new UpdateUserCommand(
            id,
            userResource.name(),
            userResource.email(),
            role,
            plan
        );

        var updatedOpt = userCommandService.handle(cmd);
        if (updatedOpt.isEmpty()) {
            // Service decided the update couldn't be performed
            return ResponseEntity.notFound().build();
        }

        var saved = updatedOpt.get();
        var resource = UserResourceFromEntityAssembler.toResourceFromEntity(saved);
        return ResponseEntity.ok(resource);
    }
}
