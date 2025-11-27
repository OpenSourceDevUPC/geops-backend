package com.geopslabs.geops.backend.sales.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para CAPA 1: Vista General del Historial de Órdenes
 *
 * Este DTO contiene solo la información resumida que se muestra
 * en la tabla/lista del historial de órdenes.
 *
 * Datos ligeros para carga rápida sin detalles innecesarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryGeneralDTO {

    /**
     * ID único del OrderHistory
     */
    private Long id;

    /**
     * ID del pago
     */
    private String paymentId;

    /**
     * Nombre del cliente que compró
     */
    private String customerName;

    /**
     * Título de la oferta comprada
     */
    private String offerTitle;

    /**
     * Nombre del proveedor/socio
     */
    private String partnerName;

    /**
     * Precio de compra
     */
    private BigDecimal purchasePrice;

    /**
     * Fecha de la compra
     */
    private Date purchaseDate;

    /**
     * Estado del pago (PENDING, COMPLETED, FAILED)
     */
    private String paymentStatus;

    /**
     * Total de cupones generados
     */
    private Integer totalCoupons;

    /**
     * Cupones que ya fueron canjeados
     */
    private Integer redeemedCoupons;

    /**
     * Cupones que aún están disponibles
     */
    private Integer pendingCoupons;

    /**
     * Cupones que ya expiraron
     */
    private Integer expiredCoupons;

    /**
     * Resumen de estado de canje
     * Por ejemplo: "2 de 5 canjeados" o "5 de 5 ✅"
     * Se calcula a partir de los contadores
     */
    private String couponStatus;
}
