package com.geopslabs.geops.backend.sales.application.services;

import com.geopslabs.geops.backend.sales.domain.model.aggregates.OrderHistory;
import com.geopslabs.geops.backend.sales.domain.repositories.OrderHistoryRepository;
import com.geopslabs.geops.backend.sales.interfaces.dto.OrderHistoryDetailedDTO;
import com.geopslabs.geops.backend.sales.interfaces.dto.OrderHistoryDTOMapper;
import com.geopslabs.geops.backend.sales.interfaces.dto.OrderHistoryGeneralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de Aplicación para OrderHistory
 *
 * Orquesta las operaciones con OrderHistory:
 * - Guarda nuevos históricos
 * - Obtiene históricos por proveedor (su vista principal)
 * - Obtiene históricos por comprador
 * - Convierte a DTOs
 */
@Service
public class OrderHistoryApplicationService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryDTOMapper dtoMapper;

    @Autowired
    public OrderHistoryApplicationService(OrderHistoryRepository orderHistoryRepository,
                                          OrderHistoryDTOMapper dtoMapper) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Guarda un nuevo OrderHistory en la base de datos
     *
     * @param orderHistory OrderHistory a guardar
     * @return OrderHistory guardado
     */
    @Transactional
    public OrderHistory saveOrderHistory(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    /**
     * Obtiene el historial de órdenes de un PROVEEDOR (su vista principal)
     *
     * IMPORTANTE: Esta es la vista que ven los proveedores
     * Se ordena por fecha descendente (más recientes primero)
     *
     * @param supplierId ID del proveedor
     * @return Lista de DTOs con información general (tabla)
     */
    @Transactional(readOnly = true)
    public List<OrderHistoryGeneralDTO> getSupplierOrderHistory(String supplierId) {
        List<OrderHistory> orders = orderHistoryRepository
                .findBySupplierIdOrderByPurchaseDateDesc(supplierId);

        return orders.stream()
                .map(dtoMapper::toGeneralDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el historial de órdenes de un COMPRADOR
     *
     * Se ordena por fecha descendente (más recientes primero)
     *
     * @param userId ID del comprador
     * @return Lista de DTOs con información general
     */
    @Transactional(readOnly = true)
    public List<OrderHistoryGeneralDTO> getCustomerOrderHistory(String userId) {
        List<OrderHistory> orders = orderHistoryRepository
                .findByUserIdOrderByPurchaseDateDesc(userId);

        return orders.stream()
                .map(dtoMapper::toGeneralDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una orden específica con todos sus detalles (vista expandida)
     *
     * Se usa cuando el usuario clickea en una orden para ver detalles completos
     *
     * @param orderHistoryId ID del OrderHistory
     * @return DTO detallado de la orden
     */
    @Transactional(readOnly = true)
    public OrderHistoryDetailedDTO getOrderHistoryDetails(Long orderHistoryId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId).orElse(null);
        return dtoMapper.toDetailedDTO(orderHistory);
    }

    /**
     * Obtiene una orden por su paymentId (único)
     *
     * @param paymentId ID del pago
     * @return DTO detallado de la orden
     */
    @Transactional(readOnly = true)
    public OrderHistoryDetailedDTO getOrderHistoryByPaymentId(String paymentId) {
        OrderHistory orderHistory = orderHistoryRepository.findByPaymentId(paymentId).orElse(null);
        return dtoMapper.toDetailedDTO(orderHistory);
    }

    /**
     * Obtiene las órdenes de un proveedor que aún tienen cupones pendientes
     *
     * Útil para notificaciones o seguimiento
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes con cupones pendientes
     */
    @Transactional(readOnly = true)
    public List<OrderHistoryGeneralDTO> getSupplierOrdersWithPendingCoupons(String supplierId) {
        List<OrderHistory> orders = orderHistoryRepository
                .findSupplierOrdersWithPendingCoupons(supplierId);

        return orders.stream()
                .map(dtoMapper::toGeneralDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza los contadores de cupones de una orden
     *
     * Se llama cuando cambia el estado de canje de los cupones
     *
     * @param orderHistoryId ID del OrderHistory
     * @param totalCoupons Total de cupones
     * @param redeemedCoupons Cupones canjeados
     * @param pendingCoupons Cupones pendientes
     * @param expiredCoupons Cupones expirados
     * @return OrderHistory actualizado como DTO
     */
    @Transactional
    public OrderHistoryDetailedDTO updateCouponCounts(
            Long orderHistoryId,
            Integer totalCoupons,
            Integer redeemedCoupons,
            Integer pendingCoupons,
            Integer expiredCoupons) {

        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId).orElse(null);
        if (orderHistory == null) {
            return null;
        }

        orderHistory.updateCouponCounts(totalCoupons, redeemedCoupons, pendingCoupons, expiredCoupons);
        OrderHistory updated = orderHistoryRepository.save(orderHistory);

        return dtoMapper.toDetailedDTO(updated);
    }
}
