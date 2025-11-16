package com.geopslabs.geops.backend.cart.interfaces.rest.transform;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import com.geopslabs.geops.backend.cart.domain.model.aggregates.CartItem;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartItemResource;
import com.geopslabs.geops.backend.cart.interfaces.rest.resources.CartResource;

import java.util.List;
import java.util.stream.Collectors;

public class CartResourceFromEntityAssembler {

    public static CartItemResource toItemResourceFromEntity(CartItem item) {
        return new CartItemResource(
                item.getId(),
                item.getUserId(),
                item.getOfferId(),
                item.getOfferTitle(),
                item.getOfferPrice(),
                item.getOfferImageUrl(),
                item.getQuantity(),
                item.getTotal()
        );
    }

    public static CartResource toResourceFromEntity(Cart cart) {
        List<CartItemResource> items = cart.getItems().stream()
                .map(CartResourceFromEntityAssembler::toItemResourceFromEntity)
                .collect(Collectors.toList());

        return new CartResource(
                cart.getId(),
                cart.getUserId(),
                items,
                cart.getTotalItems(),
                cart.getTotalAmount(),
                cart.getCreatedAt() != null ? cart.getCreatedAt().toString() : null,
                cart.getUpdatedAt() != null ? cart.getUpdatedAt().toString() : null
        );
    }
}

