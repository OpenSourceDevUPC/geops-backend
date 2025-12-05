package com.geopslabs.geops.backend.identity.application.internal.queryservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsOwner;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetDetailsOwnerByUserIdQuery;
import com.geopslabs.geops.backend.identity.domain.services.DetailsOwnerQueryService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.DetailsOwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DetailsOwnerQueryServiceImpl
 *
 * Implementation of the DetailsOwnerQueryService that handles all query operations
 * for owner details. This service implements the business logic for retrieving
 * owner details following DDD principles
 *
 * @summary Implementation of owner details query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class DetailsOwnerQueryServiceImpl implements DetailsOwnerQueryService {

    private final DetailsOwnerRepository detailsOwnerRepository;

    /**
     * Constructor for dependency injection
     *
     * @param detailsOwnerRepository The repository for owner details data access
     */
    public DetailsOwnerQueryServiceImpl(DetailsOwnerRepository detailsOwnerRepository) {
        this.detailsOwnerRepository = detailsOwnerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsOwner> handle(GetDetailsOwnerByUserIdQuery query) {
        try {
            return detailsOwnerRepository.findByUserId(query.userId());
        } catch (Exception e) {
            System.err.println("Error retrieving owner details by user ID: " + e.getMessage());
            return Optional.empty();
        }
    }
}

