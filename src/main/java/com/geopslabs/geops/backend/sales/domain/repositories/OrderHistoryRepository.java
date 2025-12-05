package com.geopslabs.geops.backend.sales.domain.repositories;

import com.geopslabs.geops.backend.sales.domain.model.aggregates.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para OrderHistory
 *
 * Define las operaciones de lectura/escritura para OrderHistory en la BD
 */
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    /**
     * Busca un OrderHistory por paymentId (único)
     *
     * @param paymentId ID del pago
     * @return OrderHistory si existe
     */
    Optional<OrderHistory> findByPaymentId(String paymentId);

    /**
     * Obtiene todos los órdenes de un comprador
     *
     * Usado para ver el historial de compras de un cliente
     *
     * @param userId ID del usuario comprador
     * @return Lista de órdenes del usuario
     */
    List<OrderHistory> findByUserIdOrderByPurchaseDateDesc(String userId);

    /**
     * Obtiene todos los órdenes de un proveedor/vendedor
     *
     * IMPORTANTE: Vista del proveedor - esto es lo que ve el vendedor
     * Filtra por supplierId (el que vende)
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes del proveedor ordenadas por fecha (más recientes primero)
     */
    List<OrderHistory> findBySupplierId(String supplierId);

    /**
     * Obtiene todos los órdenes de un proveedor ordenadas por fecha descendente
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes ordenadas por fecha descendente
     */
    List<OrderHistory> findBySupplierIdOrderByPurchaseDateDesc(String supplierId);

    /**
     * Obtiene todas las órdenes de una oferta específica
     *
     * @param offerId ID de la oferta
     * @return Lista de órdenes de esa oferta
     */
    List<OrderHistory> findByOfferId(Long offerId);

    /**
     * Obtiene todas las órdenes donde hay cupones pendientes de canjear
     *
     * Útil para promociones o seguimiento
     *
     * @return Lista de órdenes con cupones pendientes
     */
    @Query("SELECT oh FROM OrderHistory oh WHERE oh.pendingCoupons > 0")
    List<OrderHistory> findOrdersWithPendingCoupons();

    /**
     * Obtiene todas las órdenes donde todos los cupones fueron canjeados
     *
     * Usado para estadísticas de ventas completadas
     *
     * @return Lista de órdenes completamente canjeadas
     */
    @Query("SELECT oh FROM OrderHistory oh WHERE oh.redeemedCoupons = oh.totalCoupons")
    List<OrderHistory> findFullyRedeemedOrders();

    /**
     * Obtiene todas las órdenes de un proveedor con cupones pendientes
     *
     * Combinación útil para el proveedor ver qué está por canjearse
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes del proveedor con cupones pendientes
     */
    @Query("SELECT oh FROM OrderHistory oh WHERE oh.supplierId = :supplierId AND oh.pendingCoupons > 0")
    List<OrderHistory> findSupplierOrdersWithPendingCoupons(@Param("supplierId") String supplierId);
}

