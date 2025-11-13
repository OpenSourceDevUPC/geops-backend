package com.geopslabs.geops.backend.cart.domain.services;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;

import java.util.Optional;

public interface CartCommandService {

    Cart saveCart(Cart cart);

    boolean deleteCart(Long id);

    Optional<Cart> createCartForUser(String userId);
}

