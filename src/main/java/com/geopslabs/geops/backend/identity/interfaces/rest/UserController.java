package com.geopslabs.geops.backend.identity.interfaces.rest;

import com.geopslabs.geops.backend.identity.domain.model.commands.DeleteUserCommand;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.backend.identity.domain.services.UserCommandService;
import com.geopslabs.geops.backend.identity.domain.services.UserQueryService;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.CreateUserResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UpdateUserResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.UserResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.geopslabs.geops.backend.identity.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.geopslabs.geops.backend.identity.interfaces.rest.transform.UserResourceFromEntityAssembler;
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
     * @param userCommandService Service for handling user commands
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
     * Creates a new user in the system
     *
     * @param resource The CreateUserResource containing user registration data
     * @return ResponseEntity containing the created user data or error status
     */
    @Operation(summary = "Create a new user", description = "Registers a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user data"),
        @ApiResponse(responseCode = "409", description = "User with email already exists")
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource resource) {
        try {
            var command = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
            var userOptional = userCommandService.handle(command);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates an existing user's information
     *
     * @param id The unique identifier of the user to update
     * @param resource The UpdateUserResource containing updated user data
     * @return ResponseEntity containing the updated user data or error status
     */
    @Operation(summary = "Update user information", description = "Updates an existing user's data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user data"),
        @ApiResponse(responseCode = "409", description = "Email already in use by another user")
    })
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(
            @Parameter(description = "User unique identifier") @PathVariable Long id,
            @RequestBody UpdateUserResource resource) {
        try {
            var command = UpdateUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
            var userOptional = userCommandService.handle(command);

            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
            return ResponseEntity.ok(userResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a user from the system
     *
     * @param id The unique identifier of the user to delete
     * @return ResponseEntity with no content if successful or error status
     */
    @Operation(summary = "Delete user", description = "Permanently removes a user from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User unique identifier") @PathVariable Long id) {
        try {
            var command = new DeleteUserCommand(id);
            boolean deleted = userCommandService.handle(command);

            if (!deleted) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

