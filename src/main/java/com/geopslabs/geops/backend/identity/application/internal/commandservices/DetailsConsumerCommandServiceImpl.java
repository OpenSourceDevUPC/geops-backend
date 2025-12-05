package com.geopslabs.geops.backend.identity.application.internal.commandservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import com.geopslabs.geops.backend.identity.domain.model.commands.CreateDetailsConsumerCommand;
import com.geopslabs.geops.backend.identity.domain.model.commands.UpdateDetailsConsumerCommand;
import com.geopslabs.geops.backend.identity.domain.services.DetailsConsumerCommandService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.DetailsConsumerRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DetailsConsumerCommandServiceImpl
 *
 * Implementation of the DetailsConsumerCommandService that handles all command operations
 * for consumer details. This service implements the business logic for creating and updating
 * consumer details following DDD principles
 *
 * @summary Implementation of consumer details command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class DetailsConsumerCommandServiceImpl implements DetailsConsumerCommandService {

    private final DetailsConsumerRepository detailsConsumerRepository;
    private final UserRepository userRepository;

    /**
     * Constructor for dependency injection
     *
     * @param detailsConsumerRepository The repository for consumer details data access
     * @param userRepository The repository for user data access
     */
    public DetailsConsumerCommandServiceImpl(DetailsConsumerRepository detailsConsumerRepository,
                                            UserRepository userRepository) {
        this.detailsConsumerRepository = detailsConsumerRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsConsumer> handle(CreateDetailsConsumerCommand command) {
        try {
            // Check if consumer details already exist for this user
            if (detailsConsumerRepository.existsByUserId(command.userId())) {
                System.err.println("Consumer details for user ID " + command.userId() + " already exist");
                return Optional.empty();
            }

            // Find the user
            var userOptional = userRepository.findById(command.userId());
            if (userOptional.isEmpty()) {
                System.err.println("User with ID " + command.userId() + " not found");
                return Optional.empty();
            }

            var user = userOptional.get();

            // Create new consumer details
            var detailsConsumer = new DetailsConsumer(
                user,
                command.categoriasFavoritas(),
                command.recibirNotificaciones(),
                command.permisoUbicacion(),
                command.direccionCasa(),
                command.direccionTrabajo(),
                command.direccionUniversidad()
            );

            // Save and return the consumer details
            var savedDetails = detailsConsumerRepository.save(detailsConsumer);
            return Optional.of(savedDetails);
        } catch (Exception e) {
            System.err.println("Error creating consumer details: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsConsumer> handle(UpdateDetailsConsumerCommand command) {
        try {
            // Find the consumer details by user ID
            var detailsOptional = detailsConsumerRepository.findByUserId(command.userId());

            if (detailsOptional.isEmpty()) {
                System.err.println("Consumer details for user ID " + command.userId() + " not found");
                return Optional.empty();
            }

            var detailsConsumer = detailsOptional.get();

            // Update consumer details
            detailsConsumer.updateConsumerDetails(
                command.categoriasFavoritas(),
                command.recibirNotificaciones(),
                command.permisoUbicacion(),
                command.direccionCasa(),
                command.direccionTrabajo(),
                command.direccionUniversidad()
            );

            // Save and return the updated consumer details
            var updatedDetails = detailsConsumerRepository.save(detailsConsumer);
            return Optional.of(updatedDetails);
        } catch (Exception e) {
            System.err.println("Error updating consumer details: " + e.getMessage());
            return Optional.empty();
        }
    }
}

