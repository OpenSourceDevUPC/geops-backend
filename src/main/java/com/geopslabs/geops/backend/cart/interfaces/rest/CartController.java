package com.geopslabs.geops.backend.cart.interfaces.rest;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.commands.*;
import com.geopslabs.geops.backend.cart.domain.model.queries.*;
import com.geopslabs.geops.backend.cart.domain.services.CartCommandService;
import com.geopslabs.geops.backend.cart.domain.services.CartQueryService;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartItemResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.UpdateCartItemQuantityResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * CartController
 *
 * REST controller that handles HTTP requests related to shopping cart operations.
 * This controller follows the CQRS pattern, separating command and query operations
 *
 * @summary REST API for cart management
 * @since 1.0
 * @author GeOps Labs
 */
@Tag(name = "Carts", description = "Cart operations and management")
@RestController
@RequestMapping(value = "/api/v1/carts", produces = APPLICATION_JSON_VALUE)
public class CartController {

    private final CartCommandService cartCommandService;
    private final CartQueryService cartQueryService;

    public CartController(CartCommandService cartCommandService, CartQueryService cartQueryService) {
        this.cartCommandService = cartCommandService;
        this.cartQueryService = cartQueryService;
    }

    @Operation(summary = "Get all carts", description = "Retrieve all shopping carts in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all carts"),
            @ApiResponse(responseCode = "404", description = "No carts found")
    })
    @GetMapping
    public ResponseEntity<List<CartResource>> getAll() {
        var query = new GetAllCartsQuery();
        var carts = cartQueryService.handle(query);
        if (carts.isEmpty()) return ResponseEntity.notFound().build();
        var resources = carts.stream()
                .map(CartResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get cart by id", description = "Retrieve a specific cart by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartResource> getById(@Parameter(description = "Cart ID") @PathVariable Long id) {
        var query = new GetCartByIdQuery(id);
        var cartOpt = cartQueryService.handle(query);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(cartOpt.get()));
    }

    @Operation(summary = "Get cart by user id", description = "Retrieve a user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the cart")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResource> getCartByUserId(
            @Parameter(description = "User ID") @PathVariable String userId
    ) {
        var query = new GetCartByUserIdQuery(userId);
        Optional<Cart> cartOpt = cartQueryService.handle(query);

        if (cartOpt.isEmpty()) {
            // Return empty cart resource
            Cart empty = new Cart(userId);
            var resource = CartResourceFromEntityAssembler.toResourceFromEntity(empty);
            return ResponseEntity.ok(resource);
        }

        var resource = CartResourceFromEntityAssembler.toResourceFromEntity(cartOpt.get());
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Create cart for user", description = "Create a new shopping cart for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CartResource> createCartForUser(@RequestParam String userId) {
        var command = new CreateCartCommand(userId);
        var created = cartCommandService.handle(command);
        if (created.isEmpty()) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(
                CartResourceFromEntityAssembler.toResourceFromEntity(created.get()),
                CREATED
        );
    }

    @Operation(summary = "Delete cart by id", description = "Delete a cart by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "Cart ID") @PathVariable Long id) {
        var command = new DeleteCartCommand(id);
        boolean deleted = cartCommandService.handle(command);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add item to cart", description = "Add an offer item to a user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartResource> addItemToCart(
            @Parameter(description = "User ID") @PathVariable String userId,
            @RequestBody CartItemResource itemResource
    ) {
        var command = AddItemToCartCommandFromResourceAssembler.toCommandFromResource(itemResource, userId);
        var updated = cartCommandService.handle(command);
        if (updated.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
    }

    @Operation(summary = "Update cart item quantity", description = "Update the quantity of an item in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    @PutMapping("/user/{userId}/items/{offerId}")
    public ResponseEntity<CartResource> updateItemQuantity(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Parameter(description = "Offer ID") @PathVariable String offerId,
            @RequestBody UpdateCartItemQuantityResource payload
    ) {
        if (payload == null || payload.quantity() == null) {
            return ResponseEntity.badRequest().build();
        }

        var command = UpdateCartItemQuantityCommandFromResourceAssembler
                .toCommandFromResource(payload, userId, offerId);
        var updated = cartCommandService.handle(command);

        if (updated.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
    }

    @Operation(summary = "Clear cart for user", description = "Remove all items from a user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@Parameter(description = "User ID") @PathVariable String userId) {
        var command = new ClearCartCommand(userId);
        var cleared = cartCommandService.handle(command);
        if (cleared.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}

