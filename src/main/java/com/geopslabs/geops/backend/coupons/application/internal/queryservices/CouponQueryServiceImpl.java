package com.geopslabs.geops.backend.coupons.application.internal.queryservices;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetAllCouponsByUserIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByCodeQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponByIdQuery;
import com.geopslabs.geops.backend.coupons.domain.model.queries.GetCouponsByPaymentIdQuery;
import com.geopslabs.geops.backend.coupons.domain.services.CouponQueryService;
import com.geopslabs.geops.backend.coupons.infrastructure.persistence.jpa.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * CouponQueryServiceImpl
 *
 * Implementation of the CouponQueryService that handles all query operations
 * for coupon management. This service implements the business logic for
 * retrieving and searching coupons following DDD principles.
 *
 * @summary Implementation of coupon query service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional(readOnly = true)
public class CouponQueryServiceImpl implements CouponQueryService {

    private final CouponRepository couponRepository;

    /**
     * Constructor for dependency injection
     *
     * @param couponRepository The repository for coupon data access
     */
    public CouponQueryServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Coupon> handle(GetCouponByIdQuery query) {
        try {
            return couponRepository.findById(query.couponId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupon by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> handle(GetAllCouponsByUserIdQuery query) {
        try {
            return couponRepository.findByUserId(query.userId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupons by user ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> handle(GetCouponsByPaymentIdQuery query) {
        try {
            return couponRepository.findByPaymentId(query.paymentId());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupons by payment ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Coupon> handle(GetCouponByCodeQuery query) {
        try {
            return couponRepository.findByCode(query.code());
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupon by code: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> getAllCoupons() {
        try {
            return couponRepository.findAll();
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving all coupons: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> getValidCouponsByUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        try {
            String currentTime = Instant.now().toString();
            return couponRepository.findValidCouponsByUserId(userId, currentTime);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving valid coupons by user ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> getExpiredCoupons() {
        try {
            String currentTime = Instant.now().toString();
            return couponRepository.findExpiredCoupons(currentTime);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving expired coupons: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves coupons by payment code.
     *
     * This method provides a way to get coupons generated from a specific payment code,
     * which can be useful for tracking and validation purposes.
     *
     * @param paymentCode The payment code used to generate coupons
     * @return A List of Coupon objects with the specified payment code
     * @throws IllegalArgumentException if paymentCode is null or empty
     */
    public List<Coupon> getCouponsByPaymentCode(String paymentCode) {
        if (paymentCode == null || paymentCode.isBlank()) {
            throw new IllegalArgumentException("paymentCode cannot be null or empty");
        }

        try {
            return couponRepository.findByPaymentCode(paymentCode);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupons by payment code: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves coupons by offer ID.
     *
     * This method finds coupons associated with a specific offer,
     * useful for offer-based coupon management and analytics.
     *
     * @param offerId The unique identifier of the offer
     * @return A List of Coupon objects associated with the specified offer
     * @throws IllegalArgumentException if offerId is null or negative
     */
    public List<Coupon> getCouponsByOfferId(Long offerId) {
        if (offerId == null || offerId <= 0) {
            throw new IllegalArgumentException("offerId cannot be null or negative");
        }

        try {
            return couponRepository.findByOfferId(offerId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupons by offer ID: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves coupons by product type.
     *
     * This method finds coupons associated with a specific product type,
     * useful for product-based coupon filtering and management.
     *
     * @param productType The product type to filter by
     * @return A List of Coupon objects with the specified product type
     * @throws IllegalArgumentException if productType is null or empty
     */
    public List<Coupon> getCouponsByProductType(String productType) {
        if (productType == null || productType.isBlank()) {
            throw new IllegalArgumentException("productType cannot be null or empty");
        }

        try {
            return couponRepository.findByProductType(productType);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error retrieving coupons by product type: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Counts coupons for a specific user.
     *
     * This method provides a quick count of coupons belonging to a user,
     * useful for analytics and user interface purposes.
     *
     * @param userId The unique identifier of the user
     * @return The count of coupons for the specified user
     * @throws IllegalArgumentException if userId is null or empty
     */
    public long getCouponCountByUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or empty");
        }

        try {
            return couponRepository.countByUserId(userId);
        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error counting coupons by user ID: " + e.getMessage());
            return 0;
        }
    }
}
