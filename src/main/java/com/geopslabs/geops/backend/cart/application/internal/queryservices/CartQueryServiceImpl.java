package com.geopslabs.geops.backend.cart.application.internal.queryservices;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.queries.*;
import com.geopslabs.geops.backend.cart.domain.services.CartQueryService;
import com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CartQueryServiceImpl
 *
 * Implementation of the CartQueryService that handles all query operations
 * for shopping carts. This service implements the business logic for
 * retrieving cart data following DDD principles
 *
 * @summary Implementation of cart query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;

    public CartQueryServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> handle(GetCartByUserIdQuery query) {
        return cartRepository.findByUserId(query.userId());
    }

    @Override
    public List<Cart> handle(GetAllCartsQuery query) {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> handle(GetCartByIdQuery query) {
        return cartRepository.findById(query.cartId());
    }
}
