package com.geopslabs.geops.backend.sales.domain.services;

import com.geopslabs.geops.backend.sales.domain.model.aggregates.OrderHistory;
import com.geopslabs.geops.backend.sales.domain.model.aggregates.PaymentMethod;
import com.geopslabs.geops.backend.sales.domain.model.aggregates.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * Servicio de dominio para crear OrderHistory
 *
 * Encapsula la lógica de negocio para crear un nuevo OrderHistory
 * a partir de datos de Payment y Offer
 */
public class OrderHistoryDomainService {

    /**
     * Crea un nuevo OrderHistory con información básica (Capa 1 - Vista General)
     *
     * Este método es simple y no requiere dependencias externas.
     * Solo crea la entidad con los datos proporcionados.
     *
     * @param paymentId ID del pago
     * @param userId ID del comprador
     * @param supplierId ID del proveedor
     * @param offerId ID de la oferta
     * @param cartId ID del carrito
     * @param customerName Nombre del cliente
     * @param offerTitle Título de la oferta
     * @param partnerName Nombre del proveedor
     * @param purchasePrice Precio de compra
     * @param purchaseDate Fecha de compra
     * @param paymentStatus Estado del pago
     * @return OrderHistory creado con información básica
     */
    public OrderHistory createOrderHistory(
            String paymentId,
            String userId,
            String supplierId,
            Long offerId,
            String cartId,
            String customerName,
            String offerTitle,
            String partnerName,
            BigDecimal purchasePrice,
            Date purchaseDate,
            PaymentStatus paymentStatus) {

        return new OrderHistory(
                paymentId,
                userId,
                supplierId,
                offerId,
                cartId,
                customerName,
                offerTitle,
                partnerName,
                purchasePrice,
                purchaseDate,
                paymentStatus
        );
    }

    /**
     * Enriquece un OrderHistory con información detallada (Capa 2 - Vista Detallada)
     *
     * Este método completa el OrderHistory con detalles adicionales
     * del cliente, pago y oferta.
     *
     * @param orderHistory OrderHistory a enriquecer
     * @param customerEmail Email del cliente
     * @param customerFirstName Primer nombre del cliente
     * @param customerLastName Apellido del cliente
     * @param paymentMethod Método de pago
     * @param paymentCode Código de referencia del pago
     * @param completedAt Fecha de completación del pago
     * @param category Categoría de la oferta
     * @param location Ubicación donde se puede usar
     * @param imageUrl URL de la imagen
     * @param offerValidTo Fecha de vencimiento de la oferta
     * @param paymentCodes Códigos de pago en JSON
     * @return OrderHistory enriquecido
     */
    public OrderHistory enrichOrderHistoryWithDetails(
            OrderHistory orderHistory,
            String customerEmail,
            String customerFirstName,
            String customerLastName,
            PaymentMethod paymentMethod,
            String paymentCode,
            Date completedAt,
            String category,
            String location,
            String imageUrl,
            LocalDate offerValidTo,
            String paymentCodes) {

        orderHistory.setDetailedInformation(
                customerEmail,
                customerFirstName,
                customerLastName,
                paymentMethod,
                paymentCode,
                completedAt,
                category,
                location,
                imageUrl,
                offerValidTo,
                paymentCodes
        );

        return orderHistory;
    }

    /**
     * Actualiza los contadores de cupones en un OrderHistory
     *
     * Se usa cuando se crean nuevos cupones o se actualiza el estado de canje
     *
     * @param orderHistory OrderHistory a actualizar
     * @param totalCoupons Total de cupones
     * @param redeemedCoupons Cupones canjeados
     * @param pendingCoupons Cupones pendientes
     * @param expiredCoupons Cupones expirados
     * @return OrderHistory actualizado
     */
    public OrderHistory updateCouponCounts(
            OrderHistory orderHistory,
            Integer totalCoupons,
            Integer redeemedCoupons,
            Integer pendingCoupons,
            Integer expiredCoupons) {

        orderHistory.updateCouponCounts(totalCoupons, redeemedCoupons, pendingCoupons, expiredCoupons);
        return orderHistory;
    }
}
