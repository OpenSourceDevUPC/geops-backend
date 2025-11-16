package com.geopslabs.geops.backend.cart.interfaces.rest.resources;

/**
 * Request DTO used to update the quantity of an item in the cart.
 */
public record UpdateCartItemQuantityResource(Integer quantity) {
}

