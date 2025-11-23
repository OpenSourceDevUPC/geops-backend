package com.geopslabs.geops.backend.cart.domain.model.commands;

/**
 * AddItemToCartCommand
 *
 * Command record that encapsulates all the necessary data to add an item to a shopping cart.
 * This command validates input data to ensure all required fields are provided
 *
 * @summary Command to add an item to a cart
 * @param userId The unique identifier of the user
 * @param offerId The unique identifier of the offer
 * @param offerTitle The title of the offer
 * @param offerPrice The price of the offer
 * @param offerImageUrl The URL of the offer image
 * @param quantity The quantity to add
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record AddItemToCartCommand(
        String userId,
        String offerId,
        String offerTitle,
        Double offerPrice,
        String offerImageUrl,
        Integer quantity
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public AddItemToCartCommand {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }
        if (offerId == null || offerId.isBlank()) {
            throw new IllegalArgumentException("offerId cannot be null or empty");
        }
        if (offerTitle == null || offerTitle.isBlank()) {
            throw new IllegalArgumentException("offerTitle cannot be null or empty");
        }
        if (offerPrice == null || offerPrice < 0) {
            throw new IllegalArgumentException("offerPrice must be non-negative");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}

