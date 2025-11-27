package com.geopslabs.geops.backend.sales.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * DTO para CAPA 2: Vista Detallada del Historial de Órdenes
 *
 * Este DTO contiene TODOS los detalles cuando el usuario expande/clickea
 * en una orden del historial.
 *
 * Incluye información completa del cliente, pago, oferta y cupones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDetailedDTO {

    // ═══════════════════════════════════════════════════════════════
    // IDS PRINCIPALES
    // ═══════════════════════════════════════════════════════════════

    /**
     * ID único del OrderHistory
     */
    private Long id;

    /**
     * ID del pago
     */
    private String paymentId;

    /**
     * ID del usuario comprador
     */
    private String userId;

    /**
     * ID del proveedor/vendedor
     */
    private String supplierId;

    /**
     * ID de la oferta
     */
    private Long offerId;

    /**
     * ID del carrito
     */
    private String cartId;

    // ═══════════════════════════════════════════════════════════════
    // INFORMACIÓN DEL CLIENTE
    // ═══════════════════════════════════════════════════════════════

    /**
     * Nombre completo del cliente
     */
    private String customerName;

    /**
     * Email del cliente
     */
    private String customerEmail;

    /**
     * Primer nombre del cliente
     */
    private String customerFirstName;

    /**
     * Apellido del cliente
     */
    private String customerLastName;

    // ═══════════════════════════════════════════════════════════════
    // INFORMACIÓN DEL PAGO
    // ═══════════════════════════════════════════════════════════════

    /**
     * Precio de la compra
     */
    private BigDecimal purchasePrice;

    /**
     * Método de pago (YAPE, CARD, BANK_TRANSFER)
     */
    private String paymentMethod;

    /**
     * Código de referencia del pago
     */
    private String paymentCode;

    /**
     * Estado del pago (PENDING, COMPLETED, FAILED)
     */
    private String paymentStatus;

    /**
     * Fecha de la compra
     */
    private Date purchaseDate;

    /**
     * Fecha en que se completó el pago
     */
    private Date completedAt;

    /**
     * Códigos de pago generados (JSON)
     */
    private String paymentCodes;

    // ═══════════════════════════════════════════════════════════════
    // INFORMACIÓN DE LA OFERTA
    // ═══════════════════════════════════════════════════════════════

    /**
     * Título de la oferta
     */
    private String offerTitle;

    /**
     * Nombre del proveedor/socio
     */
    private String partnerName;

    /**
     * Categoría de la oferta
     */
    private String category;

    /**
     * Ubicación donde se puede usar
     */
    private String location;

    /**
     * URL de la imagen de la oferta
     */
    private String imageUrl;

    /**
     * Fecha de vencimiento de la oferta
     */
    private LocalDate offerValidTo;

    // ═══════════════════════════════════════════════════════════════
    // INFORMACIÓN DE CUPONES (Estado de Canje)
    // ═══════════════════════════════════════════════════════════════

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
     * Resumen visual del estado de canje
     * Ejemplo: "2 de 5 canjeados"
     */
    private String couponStatus;

    // ═══════════════════════════════════════════════════════════════
    // AUDITORIA
    // ═══════════════════════════════════════════════════════════════

    /**
     * Fecha de creación del registro
     */
    private Date createdAt;

    /**
     * Fecha de última actualización
     */
    private Date updatedAt;
}
