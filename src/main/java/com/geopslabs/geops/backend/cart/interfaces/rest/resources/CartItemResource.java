package com.geopslabs.geops.backend.cart.interfaces.rest.resources;

/**
 * DTO for cart item in REST responses
 */
public record CartItemResource(
    Long id,
    String userId,
    String offerId,
    String offerTitle,
    Double offerPrice,
    String offerImageUrl,
    Integer quantity,
    Double total
) {
}

