package com.geopslabs.geops.backend.identity.application.internal.commandservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.identity.domain.model.commands.CreateUserCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.DeleteUserCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateUserCommand;
import com.geopslabs.geops.backend.identity.domain.services.UserCommandService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.notifications.application.internal.outboundservices.NotificationFactoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * UserCommandServiceImpl
 *
 * Implementation of the UserCommandService that handles all command operations
 * for users. This service implements the business logic for creating, updating,
 * and deleting users following DDD principles
 *
 * @summary Implementation of user command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final NotificationFactoryService notificationFactory;

    /**
     * Constructor for dependency injection
     *
     * @param userRepository The repository for user data access
     * @param notificationFactory Service to create notifications
     */
    public UserCommandServiceImpl(
        UserRepository userRepository,
        NotificationFactoryService notificationFactory
    ) {
        this.userRepository = userRepository;
        this.notificationFactory = notificationFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(CreateUserCommand command) {
        try {
            // Check if user with email already exists
            if (userRepository.existsByEmail(command.email())) {
                System.err.println("User with email " + command.email() + " already exists");
                return Optional.empty();
            }

            // Check if user with phone already exists
            if (userRepository.existsByPhone(command.phone())) {
                System.err.println("User with phone " + command.phone() + " already exists");
                return Optional.empty();
            }

            // Create new user
            var user = new User(
                command.name(),
                command.email(),
                command.phone(),
                command.password(),
                command.role(),
                command.plan()
            );

            // Save and return the user
            var savedUser = userRepository.save(user);
            
            // Create notification if user is PREMIUM
            if ("PREMIUM".equals(command.plan())) {
                notificationFactory.createPremiumUpgradeNotification(savedUser.getId());
            }
            
            return Optional.of(savedUser);
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        try {
            // Find the user by ID
            var userOptional = userRepository.findById(command.id());

            if (userOptional.isEmpty()) {
                System.err.println("User with ID " + command.id() + " not found");
                return Optional.empty();
            }

            var user = userOptional.get();

            // Check if email is being changed and if it already exists
            if (command.email() != null &&
                !command.email().equals(user.getEmail()) &&
                userRepository.existsByEmail(command.email())) {
                System.err.println("Email " + command.email() + " is already in use");
                return Optional.empty();
            }

            // Check if phone is being changed and if it already exists
            if (command.phone() != null &&
                !command.phone().equals(user.getPhone()) &&
                userRepository.existsByPhone(command.phone())) {
                System.err.println("Phone " + command.phone() + " is already in use");
                return Optional.empty();
            }

            // Update user information
            user.updateUser(
                command.name(),
                command.email(),
                command.phone(),
                command.role(),
                command.plan()
            );

            // Save and return the updated user
            var updatedUser = userRepository.save(user);
            
            // Create notification for profile update
            notificationFactory.createProfileUpdateNotification(updatedUser.getId());
            
            // Create notification if user upgraded to PREMIUM
            if ("PREMIUM".equals(command.plan()) && !"PREMIUM".equals(user.getPlan())) {
                notificationFactory.createPremiumUpgradeNotification(updatedUser.getId());
            }
            
            return Optional.of(updatedUser);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handle(DeleteUserCommand command) {
        try {
            // Check if user exists
            if (!userRepository.existsById(command.id())) {
                System.err.println("User with ID " + command.id() + " not found");
                return false;
            }

            // Delete the user
            userRepository.deleteById(command.id());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}

