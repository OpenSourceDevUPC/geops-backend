package com.geopslabs.geops.backend.cart.domain.services;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;

import java.util.List;
import java.util.Optional;

public interface CartQueryService {

    Optional<Cart> getCartByUserId(String userId);

    List<Cart> getAllCarts();

    Optional<Cart> getCartById(Long id);
}
