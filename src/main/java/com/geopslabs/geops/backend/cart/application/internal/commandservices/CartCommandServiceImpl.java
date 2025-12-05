package com.geopslabs.geops.backend.cart.application.internal.commandservices;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.aggregates.CartItem;
import com.geopslabs.geops.backend.cart.domain.model.commands.*;
import com.geopslabs.geops.backend.cart.domain.services.CartCommandService;
import com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa.CartRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.offers.infrastructure.persistence.jpa.OfferRepository;
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
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    public CartCommandServiceImpl(CartRepository cartRepository,
                                  UserRepository userRepository,
                                  OfferRepository offerRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<Cart> handle(CreateCartCommand command) {
        try {
            var userOptional = userRepository.findById(command.userId());
            if (userOptional.isEmpty()) {
                System.err.println("User not found: " + command.userId());
                return Optional.empty();
            }

            Cart cart = new Cart(userOptional.get());
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
            // Load user and offer entities
            var userOptional = userRepository.findById(command.userId());
            var offerOptional = offerRepository.findById(command.offerId());

            if (userOptional.isEmpty()) {
                System.err.println("User not found: " + command.userId());
                return Optional.empty();
            }
            if (offerOptional.isEmpty()) {
                System.err.println("Offer not found: " + command.offerId());
                return Optional.empty();
            }

            var cartOpt = cartRepository.findByUser_Id(command.userId());
            Cart cart = cartOpt.orElseGet(() -> new Cart(userOptional.get()));

            CartItem item = new CartItem(
                    userOptional.get(),
                    offerOptional.get(),
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
            var cartOpt = cartRepository.findByUser_Id(command.userId());
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
            var cartOpt = cartRepository.findByUser_Id(command.userId());
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

