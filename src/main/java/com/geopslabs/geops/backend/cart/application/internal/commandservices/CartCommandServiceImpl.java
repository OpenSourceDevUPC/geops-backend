package com.geopslabs.geops.backend.cart.application.internal.commandservices;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.services.CartCommandService;
import com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartCommandServiceImpl implements CartCommandService {

    private final CartRepository cartRepository;

    public CartCommandServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public boolean deleteCart(Long id) {
        if (!cartRepository.existsById(id)) return false;
        cartRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Cart> createCartForUser(String userId) {
        if (userId == null || userId.isBlank()) return Optional.empty();
        Cart cart = new Cart(userId);
        var saved = cartRepository.save(cart);
        return Optional.of(saved);
    }
}

