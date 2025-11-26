package com.geopslabs.geops.backend.identity.domain.model.queries;

/**
 * GetUserByPhoneQuery
 *
 * Query record for retrieving a user by their phone number.
 *
 * @summary Query to find a user by phone
 * @param phone The phone number to search for
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record GetUserByPhoneQuery(String phone) {
    /**
     * Compact constructor that validates the query parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public GetUserByPhoneQuery {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
    }
}

