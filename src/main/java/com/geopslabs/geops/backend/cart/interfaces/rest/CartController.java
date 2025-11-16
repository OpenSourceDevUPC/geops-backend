package com.geopslabs.geops.backend.cart.interfaces.rest;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.aggregates.CartItem;
import com.geopslabs.geops.backend.cart.domain.services.CartCommandService;
import com.geopslabs.geops.backend.cart.domain.services.CartQueryService;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartItemResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.UpdateCartItemQuantityResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.transform.CartResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Cart", description = "Cart operations and management")
@RestController
@RequestMapping(value = "/api/v1/carts", produces = APPLICATION_JSON_VALUE)
public class CartController {

    private final CartCommandService cartCommandService;
    private final CartQueryService cartQueryService;

    public CartController(CartCommandService cartCommandService, CartQueryService cartQueryService) {
        this.cartCommandService = cartCommandService;
        this.cartQueryService = cartQueryService;
    }

    @Operation(summary = "Get all carts")
    @GetMapping
    public ResponseEntity<List<CartResource>> getAll() {
        var carts = cartQueryService.getAllCarts();
        var resources = carts.stream().map(CartResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get cart by id")
    @GetMapping("/{id}")
    public ResponseEntity<CartResource> getById(@PathVariable Long id) {
        var cartOpt = cartQueryService.getCartById(id);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(cartOpt.get()));
    }

    @Operation(summary = "Get cart by user id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResource> getCartByUserId(@Parameter(description = "User ID") @PathVariable String userId) {
        Optional<Cart> cartOpt = cartQueryService.getCartByUserId(userId);
        if (cartOpt.isEmpty()) {
            // return empty cart resource
            Cart empty = new Cart(userId);
            var resource = CartResourceFromEntityAssembler.toResourceFromEntity(empty);
            return ResponseEntity.ok(resource);
        }

        var resource = CartResourceFromEntityAssembler.toResourceFromEntity(cartOpt.get());
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Create cart for user")
    @PostMapping
    public ResponseEntity<CartResource> createCartForUser(@RequestParam String userId) {
        var created = cartCommandService.createCartForUser(userId);
        if (created.isEmpty()) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(CartResourceFromEntityAssembler.toResourceFromEntity(created.get()), CREATED);
    }

    @Operation(summary = "Update cart by id")
    @PutMapping("/{id}")
    public ResponseEntity<CartResource> updateById(@PathVariable Long id, @RequestBody CartResource resource) {
        var cartOpt = cartQueryService.getCartById(id);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        var cart = cartOpt.get();
        // naive mapping: replace items and totals from resource
        cart.getItems().clear();
        resource.items().forEach(ir -> cart.addItem(new CartItem(ir.userId(), ir.offerId(), ir.offerTitle(), ir.offerPrice(), ir.offerImageUrl(), ir.quantity())));
        cart.recalculateTotals();
        var saved = cartCommandService.saveCart(cart);
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(saved));
    }

    @Operation(summary = "Delete cart by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = cartCommandService.deleteCart(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartResource> addItemToCart(@PathVariable String userId, @RequestBody CartItemResource itemResource) {
        var cartOpt = cartQueryService.getCartByUserId(userId);
        Cart cart = cartOpt.orElseGet(() -> new Cart(userId));

        CartItem item = new CartItem(itemResource.userId(), itemResource.offerId(), itemResource.offerTitle(), itemResource.offerPrice(), itemResource.offerImageUrl(), itemResource.quantity());
        cart.addItem(item);

        var saved = cartCommandService.saveCart(cart);
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(saved));
    }

    @Operation(summary = "Update cart item quantity")
    @PutMapping("/user/{userId}/items/{offerId}")
    public ResponseEntity<CartResource> updateItemQuantity(@PathVariable String userId,
                                                           @PathVariable String offerId,
                                                           @RequestBody UpdateCartItemQuantityResource payload) {
        if (payload == null || payload.quantity() == null) {
            return ResponseEntity.badRequest().build();
        }

        int quantity = payload.quantity();

        var cartOpt = cartQueryService.getCartByUserId(userId);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();

        Cart cart = cartOpt.get();
        var itemOpt = cart.getItems().stream().filter(i -> i.getOfferId().equals(offerId)).findFirst();
        if (itemOpt.isEmpty()) return ResponseEntity.notFound().build();

        var item = itemOpt.get();
        if (quantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(quantity);
            cart.recalculateTotals();
        }

        var saved = cartCommandService.saveCart(cart);
        return ResponseEntity.ok(CartResourceFromEntityAssembler.toResourceFromEntity(saved));
    }

    @Operation(summary = "Clear cart for user")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        var cartOpt = cartQueryService.getCartByUserId(userId);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        var cart = cartOpt.get();
        cart.getItems().clear();
        cart.recalculateTotals();
        cartCommandService.saveCart(cart);
        return ResponseEntity.noContent().build();
    }
}
