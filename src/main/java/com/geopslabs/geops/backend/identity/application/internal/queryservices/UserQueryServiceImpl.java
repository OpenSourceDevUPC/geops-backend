package com.geopslabs.geops.backend.identity.application.internal.queryservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetAllUsersQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByEmailQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByIdQuery;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetUserByPhoneQuery;
import com.geopslabs.geops.backend.identity.domain.services.UserQueryService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserQueryServiceImpl
 *
 * Implementation of the UserQueryService that handles all query operations
 * for users. This service implements the business logic for retrieving and
 * searching users following DDD principles
 *
 * @summary Implementation of user query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    /**
     * Constructor for dependency injection
     *
     * @param userRepository The repository for user data access
     */
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> handle(GetAllUsersQuery query) {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        try {
            return userRepository.findById(query.id());
        } catch (Exception e) {
            System.err.println("Error retrieving user by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        try {
            return userRepository.findByEmail(query.email());
        } catch (Exception e) {
            System.err.println("Error retrieving user by email: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(GetUserByPhoneQuery query) {
        try {
            return userRepository.findByPhone(query.phone());
        } catch (Exception e) {
            System.err.println("Error retrieving user by phone: " + e.getMessage());
            return Optional.empty();
        }
    }
}

