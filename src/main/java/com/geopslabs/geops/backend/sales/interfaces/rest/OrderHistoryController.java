package com.geopslabs.geops.backend.sales.interfaces.rest;

import com.geopslabs.geops.backend.sales.application.services.OrderHistoryApplicationService;
import com.geopslabs.geops.backend.sales.interfaces.rest.resources.OrderHistoryDetailResource;
import com.geopslabs.geops.backend.sales.interfaces.rest.resources.OrderHistoryResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para OrderHistory
 *
 * Expone endpoints para:
 * - Obtener historial de órdenes por proveedor (su vista principal)
 * - Obtener historial de órdenes por comprador
 * - Obtener detalles completos de una orden
 * - Actualizar contadores de cupones
 */
@RestController
@RequestMapping("/api/v1/sales/order-history")
@CrossOrigin(origins = "*")
@Tag(name = "Sales", description = "Gestión de historial de órdenes de compra")
public class OrderHistoryController {

    private final OrderHistoryApplicationService orderHistoryService;

    @Autowired
    public OrderHistoryController(OrderHistoryApplicationService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    /**
     * VISTA DEL PROVEEDOR: Obtiene todas sus órdenes (tabla general)
     *
     * Endpoint principal para los proveedores ver su historial de ventas
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes con información resumida
     */
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<OrderHistoryResource>> getSupplierOrderHistory(
            @PathVariable String supplierId) {

        List<OrderHistoryResource> orders = orderHistoryService.getSupplierOrderHistory(supplierId);
        return ResponseEntity.ok(orders);
    }

    /**
     * VISTA DEL COMPRADOR: Obtiene todas sus órdenes (tabla general)
     *
     * Endpoint para que los compradores vean su historial de compras
     *
     * @param userId ID del comprador
     * @return Lista de órdenes con información resumida
     */
    @GetMapping("/customer/{userId}")
    public ResponseEntity<List<OrderHistoryResource>> getCustomerOrderHistory(
            @PathVariable String userId) {

        List<OrderHistoryResource> orders = orderHistoryService.getCustomerOrderHistory(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * VISTA DETALLADA: Obtiene todos los detalles de una orden específica
     *
     * Se llama cuando el usuario clickea en una orden para expandir detalles
     *
     * @param orderHistoryId ID del OrderHistory
     * @return Todos los detalles de la orden
     */
    @GetMapping("/{orderHistoryId}")
    public ResponseEntity<OrderHistoryDetailResource> getOrderHistoryDetails(
            @PathVariable Long orderHistoryId) {

        OrderHistoryDetailResource orderDetails = orderHistoryService.getOrderHistoryDetails(orderHistoryId);

        if (orderDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderDetails);
    }

    /**
     * Obtiene una orden por su paymentId
     *
     * Útil para buscar una orden por su ID de pago
     *
     * @param paymentId ID del pago
     * @return Todos los detalles de la orden
     */
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<OrderHistoryDetailResource> getOrderHistoryByPaymentId(
            @PathVariable String paymentId) {

        OrderHistoryDetailResource orderDetails = orderHistoryService.getOrderHistoryByPaymentId(paymentId);

        if (orderDetails == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderDetails);
    }

    /**
     * VISTA DEL PROVEEDOR: Obtiene órdenes pendientes de canje (seguimiento)
     *
     * Útil para que el proveedor vea qué órdenes tienen cupones sin canjear
     *
     * @param supplierId ID del proveedor
     * @return Lista de órdenes con cupones pendientes
     */
    @GetMapping("/supplier/{supplierId}/pending-coupons")
    public ResponseEntity<List<OrderHistoryResource>> getSupplierOrdersWithPendingCoupons(
            @PathVariable String supplierId) {

        List<OrderHistoryResource> orders = orderHistoryService
                .getSupplierOrdersWithPendingCoupons(supplierId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Actualiza los contadores de cupones de una orden
     *
     * Se llama desde el módulo de coupons cuando cambia el estado de canje
     *
     * @param orderHistoryId ID del OrderHistory
     * @param totalCoupons Total de cupones
     * @param redeemedCoupons Cupones canjeados
     * @param pendingCoupons Cupones pendientes
     * @param expiredCoupons Cupones expirados
     * @return Orden actualizada
     */
    @PutMapping("/{orderHistoryId}/coupon-counts")
    public ResponseEntity<OrderHistoryDetailResource> updateCouponCounts(
            @PathVariable Long orderHistoryId,
            @RequestParam Integer totalCoupons,
            @RequestParam Integer redeemedCoupons,
            @RequestParam Integer pendingCoupons,
            @RequestParam Integer expiredCoupons) {

        OrderHistoryDetailResource updated = orderHistoryService.updateCouponCounts(
                orderHistoryId,
                totalCoupons,
                redeemedCoupons,
                pendingCoupons,
                expiredCoupons
        );

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
