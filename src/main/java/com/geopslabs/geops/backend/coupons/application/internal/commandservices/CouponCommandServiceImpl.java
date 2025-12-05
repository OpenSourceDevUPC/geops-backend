package com.geopslabs.geops.backend.coupons.application.internal.commandservices;

import com.geopslabs.geops.backend.coupons.domain.model.aggregates.Coupon;
import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateManyCouponsCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.UpdateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.services.CouponCommandService;
import com.geopslabs.geops.backend.coupons.infrastructure.persistence.jpa.CouponRepository;
import com.geopslabs.geops.backend.identity.infrastructure.persistence.jpa.UserRepository;
import com.geopslabs.geops.backend.payments.infrastructure.persistence.jpa.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CouponCommandServiceImpl
 *
 * Implementation of the CouponCommandService that handles all command operations
 * for coupon management. This service implements the business logic for
 * creating, updating, and managing coupons following DDD principles.
 *
 * @summary Implementation of coupon command service operations
 * @since 1.0
 * @author GeOps Labs
 */
@Service
@Transactional
public class CouponCommandServiceImpl implements CouponCommandService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    /**
     * Constructor for dependency injection
     *
     * @param couponRepository The repository for coupon data access
     * @param userRepository The repository for user data access
     * @param paymentRepository The repository for payment data access
     */
    public CouponCommandServiceImpl(CouponRepository couponRepository,
                                    UserRepository userRepository,
                                    PaymentRepository paymentRepository) {
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Coupon> handle(CreateCouponCommand command) {
        try {
            // Check if coupon code already exists to ensure uniqueness
            if (couponRepository.existsByCode(command.code())) {
                throw new IllegalArgumentException("Coupon code already exists: " + command.code());
            }

            // Fetch user and payment entities
            var userOptional = userRepository.findById(command.userId());
            var paymentOptional = paymentRepository.findById(command.paymentId());

            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User not found: " + command.userId());
            }
            if (paymentOptional.isEmpty()) {
                throw new IllegalArgumentException("Payment not found: " + command.paymentId());
            }

            // Create new coupon from command with entities
            var coupon = new Coupon(command, userOptional.get(), paymentOptional.get());

            // Save the coupon to the repository
            var savedCoupon = couponRepository.save(coupon);

            return Optional.of(savedCoupon);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error creating coupon: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coupon> handle(CreateManyCouponsCommand command) {
        List<Coupon> createdCoupons = new ArrayList<>();

        try {
            // Process each coupon creation command
            for (CreateCouponCommand couponCommand : command.coupons()) {
                try {
                    // Check if coupon code already exists
                    if (!couponRepository.existsByCode(couponCommand.code())) {
                        // Fetch user and payment entities
                        var userOptional = userRepository.findById(couponCommand.userId());
                        var paymentOptional = paymentRepository.findById(couponCommand.paymentId());

                        if (userOptional.isPresent() && paymentOptional.isPresent()) {
                            var coupon = new Coupon(couponCommand, userOptional.get(), paymentOptional.get());
                            var savedCoupon = couponRepository.save(coupon);
                            createdCoupons.add(savedCoupon);
                        } else {
                            System.err.println("User or Payment not found for coupon: " + couponCommand.code());
                        }
                    } else {
                        // Log duplicate code warning but continue processing
                        System.err.println("Skipping duplicate coupon code: " + couponCommand.code());
                    }
                } catch (Exception e) {
                    // Log individual coupon creation error but continue with others
                    System.err.println("Error creating individual coupon: " + e.getMessage());
                }
            }

            return createdCoupons;

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error creating multiple coupons: " + e.getMessage());
            return createdCoupons; // Return partial results
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Coupon> handle(UpdateCouponCommand command) {
        try {
            // Find the existing coupon by ID
            var existingCouponOpt = couponRepository.findById(command.couponId());

            if (existingCouponOpt.isEmpty()) {
                return Optional.empty();
            }

            var existingCoupon = existingCouponOpt.get();

            // Check if new code is unique (if being updated)
            if (command.code() != null && !command.code().equals(existingCoupon.getCode())) {
                if (couponRepository.existsByCode(command.code())) {
                    throw new IllegalArgumentException("Coupon code already exists: " + command.code());
                }
            }

            // Update the coupon with new data
            existingCoupon.updateCoupon(command);

            // Save the updated coupon
            var updatedCoupon = couponRepository.save(existingCoupon);

            return Optional.of(updatedCoupon);

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error updating coupon: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCoupon(Long couponId) {
        if (couponId == null || couponId <= 0) {
            throw new IllegalArgumentException("couponId cannot be null or negative");
        }

        try {
            // First check if coupon exists
            var existingCouponOpt = couponRepository.findById(couponId);

            if (existingCouponOpt.isEmpty()) {
                return false;
            }

            // Delete the coupon
            couponRepository.deleteById(couponId);

            return true;

        } catch (Exception e) {
            // Log the error (in a real application, use proper logging framework)
            System.err.println("Error deleting coupon: " + e.getMessage());
            return false;
        }
    }
}

