package com.geopslabs.geops.backend.cart.infrastructure.persistence.jpa;

import com.geopslabs.geops.backend.cart.domain.model.aggregates.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for Cart aggregate
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser_Id(Long userId);
}

