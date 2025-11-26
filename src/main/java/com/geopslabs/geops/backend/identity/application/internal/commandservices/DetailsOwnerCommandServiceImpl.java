package com.geopslabs.geops.backend.identity.application.internal.commandservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import com.geopslabs.geops.backend.identity.domain.model.commands.CreateDetailsOwnerCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateDetailsOwnerCommand;
import com.geopslabs.geops.backend.identity.domain.services.DetailsOwnerCommandService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.DetailsOwnerRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DetailsOwnerCommandServiceImpl
 *
 * Implementation of the DetailsOwnerCommandService that handles all command operations
 * for owner details. This service implements the business logic for creating and updating
 * owner details following DDD principles
 *
 * @summary Implementation of owner details command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class DetailsOwnerCommandServiceImpl implements DetailsOwnerCommandService {

    private final DetailsOwnerRepository detailsOwnerRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for dependency injection
     *
     * @param detailsOwnerRepository The repository for owner details data access
     * @param userRepository The repository for user data access
     */
    public DetailsOwnerCommandServiceImpl(DetailsOwnerRepository detailsOwnerRepository,
                                         UserRepository userRepository) {
        this.detailsOwnerRepository = detailsOwnerRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsOwner> handle(CreateDetailsOwnerCommand command) {
        try {
            // Check if owner details already exist for this user
            if (detailsOwnerRepository.existsByUserId(command.userId())) {
                System.err.println("Owner details for user ID " + command.userId() + " already exist");
                return Optional.empty();
            }

            // Find the user
            var userOptional = userRepository.findById(command.userId());
            if (userOptional.isEmpty()) {
                System.err.println("User with ID " + command.userId() + " not found");
                return Optional.empty();
            }

            var user = userOptional.get();

            // Create new owner details
            var detailsOwner = new DetailsOwner(
                user,
                command.businessName(),
                command.businessType(),
                command.taxId(),
                command.website(),
                command.description(),
                command.address(),
                command.horarioAtencion()
            );

            // Save and return the owner details
            var savedDetails = detailsOwnerRepository.save(detailsOwner);
            return Optional.of(savedDetails);
        } catch (Exception e) {
            System.err.println("Error creating owner details: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsOwner> handle(UpdateDetailsOwnerCommand command) {
        try {
            // Find the owner details by user ID
            var detailsOptional = detailsOwnerRepository.findByUserId(command.userId());

            if (detailsOptional.isEmpty()) {
                System.err.println("Owner details for user ID " + command.userId() + " not found");
                return Optional.empty();
            }

            var detailsOwner = detailsOptional.get();

            // Update owner details
            detailsOwner.updateOwnerDetails(
                command.businessName(),
                command.businessType(),
                command.taxId(),
                command.website(),
                command.description(),
                command.address(),
                command.horarioAtencion()
            );

            // Save and return the updated owner details
            var updatedDetails = detailsOwnerRepository.save(detailsOwner);
            return Optional.of(updatedDetails);
        } catch (Exception e) {
            System.err.println("Error updating owner details: " + e.getMessage());
            return Optional.empty();
        }
    }
}

