package com.geopslabs.geops.backend.identity.application.internal.queryservices;

import com.geopslabs.geops.backend.identity.domain.model.aggregates.DetailsConsumer;
import com.geopslabs.geops.backend.identity.domain.model.queries.GetDetailsConsumerByUserIdQuery;
import com.geopslabs.geops.backend.identity.domain.services.DetailsConsumerQueryService;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.DetailsConsumerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DetailsConsumerQueryServiceImpl
 *
 * Implementation of the DetailsConsumerQueryService that handles all query operations
 * for consumer details. This service implements the business logic for retrieving
 * consumer details following DDD principles
 *
 * @summary Implementation of consumer details query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class DetailsConsumerQueryServiceImpl implements DetailsConsumerQueryService {

    private final DetailsConsumerRepository detailsConsumerRepository;

    /**
     * Constructor for dependency injection
     *
     * @param detailsConsumerRepository The repository for consumer details data access
     */
    public DetailsConsumerQueryServiceImpl(DetailsConsumerRepository detailsConsumerRepository) {
        this.detailsConsumerRepository = detailsConsumerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DetailsConsumer> handle(GetDetailsConsumerByUserIdQuery query) {
        try {
            return detailsConsumerRepository.findByUserId(query.userId());
        } catch (Exception e) {
            System.err.println("Error retrieving consumer details by user ID: " + e.getMessage());
            return Optional.empty();
        }
    }
}

