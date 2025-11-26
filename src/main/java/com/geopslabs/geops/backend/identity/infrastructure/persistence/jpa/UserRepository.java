package com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 *
 * JPA Repository interface for User aggregate root
 * This repository provides data access operations for users
 * including custom queries for user management and authentication operations
 *
 * @summary JPA Repository for user data access operations
 * @since 1.0
 * @author GeOps Labs
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address
     *
     * @param email The email address to search for
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their phone number
     *
     * @param phone The phone number to search for
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> findByPhone(String phone);

    /**
     * Checks if a user exists with the given email
     *
     * @param email The email address to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given phone number
     *
     * @param phone The phone number to check
     * @return true if a user exists with the phone, false otherwise
     */
    boolean existsByPhone(String phone);
}

