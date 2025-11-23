package com.geopslabs.geops.backend.cart.domain.services;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * CartQueryService
 *
 * Domain service interface that defines query operations for shopping carts.
 * This service handles all read operations following the
 * Command Query Responsibility Segregation (CQRS) pattern
 *
 * @summary Service interface for handling cart query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface CartQueryService {

    /**
     * Handles retrieving a cart by user ID
     *
     * @param query The query containing the user ID
     * @return An Optional containing the Cart if found, empty otherwise
     */
    Optional<Cart> handle(GetCartByUserIdQuery query);

    /**
     * Handles retrieving all carts
     *
     * @param query The query (no parameters needed)
     * @return A list of all carts
     */
    List<Cart> handle(GetAllCartsQuery query);

    /**
     * Handles retrieving a cart by its ID
     *
     * @param query The query containing the cart ID
     * @return An Optional containing the Cart if found, empty otherwise
     */
    Optional<Cart> handle(GetCartByIdQuery query);
}
