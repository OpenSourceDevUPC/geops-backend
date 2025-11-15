package com.geopslabs.geops.backend.identity.domain.services;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetAllUsersQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * UserQueryService
 *
 * Service interface for handling user query operations
 * This service defines methods for retrieving user information
 * following the DDD pattern
 *
 * @summary Service for user query operations
 * @since 1.0
 * @author GeOps Labs
 */
public interface UserQueryService {

    /**
     * Handles the query to get all users
     *
     * @param query The GetAllUsersQuery
     * @return A list of all users in the system
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handles the query to get a user by ID
     *
     * @param query The GetUserByIdQuery containing the user ID
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handles the query to get a user by email
     *
     * @param query The GetUserByEmailQuery containing the user email
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> handle(GetUserByEmailQuery query);
}

