package com.geopslabs.geops.backend.cart.application.internal.queryservices;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.services.CartQueryService;
import com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;

    public CartQueryServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> getCartByUserId(String userId) {
        if (userId == null || userId.isBlank()) return Optional.empty();
        return cartRepository.findByUserId(userId);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> getCartById(Long id) {
        if (id == null || id <= 0) return Optional.empty();
        return cartRepository.findById(id);
    }
}
