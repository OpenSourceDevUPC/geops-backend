package com.geopslabs.geops.backend.favorites.application.internal.commandservices;

import com.geopslabs.geops.backend.favorites.domain.model.aggregates.Favorite;
import com.geopslabs.geops.backend.favorites.domain.model.commands.CreateFavoriteCommand;
import com.geopslabs.geops.backend.favorites.domain.model.commands.DeleteFavoriteCommand;
import com.geopslabs.geops.backend.favorites.domain.services.FavoriteCommandService;
import com.geopslabs.geops.backend.favorites.infrastructure.persistence.jpa.FavoriteRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.notifications.application.internal.outboundservices.NotificationFactoryService;
import com.geopslabs.geops.backend.offers.infrastructure.persistence.jpa.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * FavoriteCommandServiceImpl
 *
 * Implementation of the FavoriteCommandService that handles all command operations
 * for favorites. This service implements the business logic for
 * creating and managing favorites following DDD principles
 *
 * @author GeOps Labs
 * @summary Implementation of favorite command service operations
 * @since 1.0
 */
@Service
@Transactional
public class FavoriteCommandServiceImpl implements FavoriteCommandService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final NotificationFactoryService notificationFactory;

    /**
     * Constructor for dependency injection
     *
     * @param favoriteRepository The repository for favorite data access
     * @param userRepository The repository for user data access
     * @param offerRepository The repository for offer data access
     * @param notificationFactory Service to create notifications
     */
    public FavoriteCommandServiceImpl(
        FavoriteRepository favoriteRepository,
        UserRepository userRepository,
        OfferRepository offerRepository,
        NotificationFactoryService notificationFactory
    ) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.notificationFactory = notificationFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Favorite> handle(CreateFavoriteCommand command) {
        try {
            // Check if favorite already exists (prevent duplicates)
            boolean exists = favoriteRepository.existsByUser_IdAndOffer_Id(
                    command.userId(),
                    command.offerId()
            );

            if (exists) {
                System.err.println("Favorite already exists for userId: " +
                        command.userId() + " and offerId: " + command.offerId());
                return Optional.empty();
            }

            // Fetch user entity
            var userOptional = userRepository.findById(command.userId());
            
            if (userOptional.isEmpty()) {
                System.err.println("User not found: " + command.userId());
                return Optional.empty();
            }

            // Fetch offer entity
            var offerOptional = offerRepository.findById(command.offerId());
            
            if (offerOptional.isEmpty()) {
                System.err.println("Offer not found: " + command.offerId());
                return Optional.empty();
            }

            // Create new favorite from command with user and offer entities
            var favorite = new Favorite(command, userOptional.get(), offerOptional.get());

            // Save the favorite to the repository
            var savedFavorite = favoriteRepository.save(favorite);

            // Create notification for favorite added
            notificationFactory.createFavoriteAddedNotification(
                command.userId(),
                command.offerId().toString(),
                "Oferta"
            );

            return Optional.of(savedFavorite);

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error creating favorite: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleDelete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or negative");
        }

        try {
            // First check if favorite exists
            if (!favoriteRepository.existsById(id)) {
                return false;
            }

            // Delete the favorite
            favoriteRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error deleting favorite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleDelete(DeleteFavoriteCommand command) {
        try {
            // First check if favorite exists
            boolean exists = favoriteRepository.existsByUser_IdAndOffer_Id(
                    command.userId(),
                    command.offerId()
            );

            if (!exists) {
                System.err.println("Favorite not found for userId: " +
                        command.userId() + " and offerId: " + command.offerId());
                return false;
            }

            // Delete the favorite by userId and offerId
            long deletedCount = favoriteRepository.deleteByUser_IdAndOffer_Id(
                    command.userId(),
                    command.offerId()
            );

            return deletedCount > 0;

        } catch (Exception e) {
            // Log the error with full stacktrace
            System.err.println("Error deleting favorite by userId and offerId: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
