package com.geopslabs.geops.backend.cart.application.internal.commandservices;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.aggregates.CartItem;
import com.geopslabs.geops.backend.cart.domain.model.commands.*;
import com.geopslabs.geops.backend.cart.domain.services.CartCommandService;
import com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * CartCommandServiceImpl
 *
 * Implementation of the CartCommandService that handles all command operations
 * for shopping carts. This service implements the business logic for
 * creating, updating, and managing carts following DDD principles
 *
 * @summary Implementation of cart command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class CartCommandServiceImpl implements CartCommandService {

    private final CartRepository cartRepository;

    public CartCommandServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> handle(CreateCartCommand command) {
        try {
            Cart cart = new Cart(command.userId());
            var saved = cartRepository.save(cart);
            return Optional.of(saved);
        } catch (Exception e) {
            System.err.println("Error creating cart: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cart> handle(AddItemToCartCommand command) {
        try {
            var cartOpt = cartRepository.findByUserId(command.userId());
            Cart cart = cartOpt.orElseGet(() -> new Cart(command.userId()));

            CartItem item = new CartItem(
                    command.userId(),
                    command.offerId(),
                    command.offerTitle(),
                    command.offerPrice(),
                    command.offerImageUrl(),
                    command.quantity()
            );
            cart.addItem(item);

            var saved = cartRepository.save(cart);
            return Optional.of(saved);
        } catch (Exception e) {
            System.err.println("Error adding item to cart: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cart> handle(UpdateCartItemQuantityCommand command) {
        try {
            var cartOpt = cartRepository.findByUserId(command.userId());
            if (cartOpt.isEmpty()) return Optional.empty();

            Cart cart = cartOpt.get();
            var itemOpt = cart.getItems().stream()
                    .filter(i -> i.getOfferId().equals(command.offerId()))
                    .findFirst();

            if (itemOpt.isEmpty()) return Optional.empty();

            var item = itemOpt.get();
            if (command.quantity() <= 0) {
                cart.removeItem(item);
            } else {
                item.setQuantity(command.quantity());
                cart.recalculateTotals();
            }

            var saved = cartRepository.save(cart);
            return Optional.of(saved);
        } catch (Exception e) {
            System.err.println("Error updating cart item quantity: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cart> handle(ClearCartCommand command) {
        try {
            var cartOpt = cartRepository.findByUserId(command.userId());
            if (cartOpt.isEmpty()) return Optional.empty();

            var cart = cartOpt.get();
            cart.getItems().clear();
            cart.recalculateTotals();

            var saved = cartRepository.save(cart);
            return Optional.of(saved);
        } catch (Exception e) {
            System.err.println("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean handle(DeleteCartCommand command) {
        try {
            if (!cartRepository.existsById(command.cartId())) return false;
            cartRepository.deleteById(command.cartId());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting cart: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

