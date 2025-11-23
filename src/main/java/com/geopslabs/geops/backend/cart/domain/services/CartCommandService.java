package com.geopslabs.geops.backend.cart.domain.services;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.commands.*;

import java.util.Optional;

/**
 * CartCommandService
 *
 * Domain service interface that defines command operations for shopping carts.
 * This service handles all write operations (Create, Update, Delete) following the
 * Command Query Responsibility Segregation (CQRS) pattern
 *
 * @summary Service interface for handling cart command operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface CartCommandService {

    /**
     * Handles the creation of a new cart for a user
     *
     * @param command The command containing the user ID
     * @return An Optional containing the created Cart if successful, empty if failed
     */
    Optional<Cart> handle(CreateCartCommand command);

    /**
     * Handles adding an item to a user's cart
     *
     * @param command The command containing item details and user ID
     * @return An Optional containing the updated Cart if successful, empty if failed
     */
    Optional<Cart> handle(AddItemToCartCommand command);

    /**
     * Handles updating the quantity of an item in a cart
     *
     * @param command The command containing user ID, offer ID, and new quantity
     * @return An Optional containing the updated Cart if successful, empty if failed
     */
    Optional<Cart> handle(UpdateCartItemQuantityCommand command);

    /**
     * Handles clearing all items from a user's cart
     *
     * @param command The command containing the user ID
     * @return An Optional containing the cleared Cart if successful, empty if failed
     */
    Optional<Cart> handle(ClearCartCommand command);

    /**
     * Handles the deletion of a cart by its unique identifier
     *
     * @param command The command containing the cart ID
     * @return true if the cart was successfully deleted, false if not found
     */
    boolean handle(DeleteCartCommand command);
}

