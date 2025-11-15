package com.geopslabs.geops.backend.identity.interfaces.rest;

import com.geopslabs.geops.backend.identity.domain.model.commands.CreateUserCommand;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.services.UserCommandService;
import com.geopslabs.geops.backend.identity.domain.services.UserQueryService;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.AuthenticationResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.SignInResource;
import com.geopslabs.geops.backend.identity.interfaces.rest.resources.SignUpResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * AuthenticationController
 *
 * REST controller that exposes authentication endpoints for the GeOps platform
 * This controller handles user sign-up and sign-in operations
 *
 * @summary REST controller for authentication operations
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Authentication", description = "User authentication and registration operations")
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    /**
     * Constructor for dependency injection
     *
     * @param userCommandService Service for handling user commands
     * @param userQueryService Service for handling user queries
     */
    public AuthenticationController(UserCommandService userCommandService,
                                   UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    /**
     * Register a new user in the system
     *
     * @param resource The sign-up request containing user registration data
     * @return ResponseEntity containing the authentication data or error status
     */
    @Operation(summary = "Register a new user", description = "Creates a new user account with default role and plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResource> signUp(@RequestBody SignUpResource resource) {
        // Validate input
        if (resource.name() == null || resource.name().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (resource.email() == null || resource.email().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (resource.password() == null || resource.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Create user with default role and plan
        var createUserCommand = new CreateUserCommand(
            resource.name(),
            resource.email(),
            resource.password(), // In production, this should be hashed
            "CONSUMER", // Default role
            "FREEMIUM"  // Default plan
        );

        var userOptional = userCommandService.handle(createUserCommand);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        var user = userOptional.get();
        var authResource = new AuthenticationResource(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getPlan(),
            "User registered successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(authResource);
    }

    /**
     * Authenticate a user with email and password
     *
     * @param resource The sign-in request containing user credentials
     * @return ResponseEntity containing the authentication data or error status
     */
    @Operation(summary = "Authenticate a user", description = "Validates user credentials and returns user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResource> signIn(@RequestBody SignInResource resource) {
        // Validate input
        if (resource.email() == null || resource.email().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (resource.password() == null || resource.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Find user by email
        var query = new GetUserByEmailQuery(resource.email());
        var userOptional = userQueryService.handle(query);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userOptional.get();

        // Validate password (simplified - in production use proper password hashing)
        if (!user.getPassword().equals(resource.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var authResource = new AuthenticationResource(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getPlan(),
            "User authenticated successfully"
        );

        return ResponseEntity.ok(authResource);
    }
}

