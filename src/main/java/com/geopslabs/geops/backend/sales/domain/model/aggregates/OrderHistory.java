package com.geopslabs.geops.backend.sales.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * OrderHistory Aggregate Root
 *
 * Representa el historial de órdenes de compra en el sistema GeOps.
 * Proporciona una vista consolidada con dos capas: general (resumen) y detallada.
 *
 * @summary Gestiona el historial de órdenes de compra
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "order_histories", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_supplier_id", columnList = "supplier_id"),
    @Index(name = "idx_payment_id", columnList = "payment_id")
})
public class OrderHistory extends AuditableAbstractAggregateRoot<OrderHistory> {

    // ═══════════════════════════════════════════════════════════════
    // CAPA 1: IDS PRINCIPALES (Vista General)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Referencia única al pago (Payment.id)
     */
    @Column(name = "payment_id", nullable = false, unique = true)
    @Getter
    private String paymentId;

    /**
     * ID del usuario que realizó la compra
     */
    @Column(name = "user_id", nullable = false)
    @Getter
    private String userId;

    /**
     * ID del proveedor/vendedor (User.id del que vende)
     * CLAVE para filtrar historial por proveedor
     */
    @Column(name = "supplier_id", nullable = false)
    @Getter
    private String supplierId;

    /**
     * ID de la oferta comprada (Coupon.offerId)
     */
    @Column(name = "offer_id", nullable = false)
    @Getter
    private Long offerId;

    /**
     * ID del carrito del que salió la orden (Payment.cartId)
     */
    @Column(name = "cart_id")
    @Getter
    private String cartId;

    // ═══════════════════════════════════════════════════════════════
    // CAPA 1: DATOS DESNORMALIZADOS (Para tabla rápida - Vista General)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Nombre del cliente que realizó la compra
     * Desnormalizado de Payment.customerFirstName + Payment.customerLastName
     */
    @Column(name = "customer_name", nullable = false)
    @Getter
    private String customerName;

    /**
     * Título de la oferta comprada
     * Desnormalizado de Offer.title
     */
    @Column(name = "offer_title", nullable = false)
    @Getter
    private String offerTitle;

    /**
     * Nombre del socio/proveedor
     * Desnormalizado de Offer.partner
     */
    @Column(name = "partner_name", nullable = false)
    @Getter
    private String partnerName;

    /**
     * Precio de compra
     * Desnormalizado de Payment.amount
     */
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    @Getter
    private BigDecimal purchasePrice;

    /**
     * Fecha de la compra
     * Desnormalizado de Payment.createdAt
     */
    @Column(name = "purchase_date", nullable = false)
    @Getter
    private Date purchaseDate;

    /**
     * Estado del pago
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    @Getter
    private PaymentStatus paymentStatus;

    // ═══════════════════════════════════════════════════════════════
    // CAPA 1: CONTADORES DE CUPONES (Vista General)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Total de cupones generados para esta orden
     */
    @Column(name = "total_coupons", nullable = false)
    @Getter
    private Integer totalCoupons;

    /**
     * Cupones que ya fueron canjeados
     */
    @Column(name = "redeemed_coupons", nullable = false)
    @Getter
    private Integer redeemedCoupons;

    /**
     * Cupones que aún están disponibles para canjear
     */
    @Column(name = "pending_coupons", nullable = false)
    @Getter
    private Integer pendingCoupons;

    /**
     * Cupones que ya expiraron
     */
    @Column(name = "expired_coupons", nullable = false)
    @Getter
    private Integer expiredCoupons;

    // ═══════════════════════════════════════════════════════════════
    // CAPA 2: DETALLES CLIENTE (Vista Detallada)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Email del cliente
     * Desnormalizado de Payment.customerEmail
     */
    @Column(name = "customer_email")
    @Getter
    private String customerEmail;

    /**
     * Primer nombre del cliente
     * Desnormalizado de Payment.customerFirstName
     */
    @Column(name = "customer_first_name")
    @Getter
    private String customerFirstName;

    /**
     * Apellido del cliente
     * Desnormalizado de Payment.customerLastName
     */
    @Column(name = "customer_last_name")
    @Getter
    private String customerLastName;

    // ═══════════════════════════════════════════════════════════════
    // CAPA 2: DETALLES PAGO (Vista Detallada)
    // ═══════════════════════════════════════════════════════════════


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    @Getter
    private PaymentMethod paymentMethod;

    /**
     * Código de referencia del pago (referencia de banco)
     * Desnormalizado de Payment.paymentCode
     */
    @Column(name = "payment_code")
    @Getter
    private String paymentCode;

    /**
     * Fecha y hora en que se completó el pago
     * Desnormalizado de Payment.completedAt
     */
    @Column(name = "completed_at")
    @Getter
    private Date completedAt;

    /**
     * Códigos de pago generados (almacenados como JSON)
     * Desnormalizado de Payment.paymentCodes
     */
    @Column(name = "payment_codes", columnDefinition = "JSON")
    @Getter
    private String paymentCodes;

    // ═══════════════════════════════════════════════════════════════
    // CAPA 2: DETALLES OFERTA (Vista Detallada)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Categoría de la oferta
     * Desnormalizado de Offer.category
     */
    @Column(name = "category")
    @Getter
    private String category;

    /**
     * Ubicación donde se puede usar la oferta
     * Desnormalizado de Offer.location
     */
    @Column(name = "location")
    @Getter
    private String location;

    /**
     * URL de la imagen de la oferta
     * Desnormalizado de Offer.imageUrl
     */
    @Column(name = "image_url")
    @Getter
    private String imageUrl;

    /**
     * Fecha de vencimiento de la oferta
     * Desnormalizado de Offer.validTo
     */
    @Column(name = "valid_to")
    @Getter
    private LocalDate offerValidTo;

    /**
     * Constructor protegido para JPA
     */
    protected OrderHistory() {
    }

    /**
     * Constructor para crear un nuevo OrderHistory
     * Se usa al crear un registro desde los datos del Payment y Offer
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
     */
    public OrderHistory(String paymentId, String userId, String supplierId,
                        Long offerId, String cartId, String customerName,
                        String offerTitle, String partnerName,
                        BigDecimal purchasePrice, Date purchaseDate,
                        PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.supplierId = supplierId;
        this.offerId = offerId;
        this.cartId = cartId;
        this.customerName = customerName;
        this.offerTitle = offerTitle;
        this.partnerName = partnerName;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.paymentStatus = paymentStatus;
        this.totalCoupons = 0;
        this.redeemedCoupons = 0;
        this.pendingCoupons = 0;
        this.expiredCoupons = 0;
    }

    /**
     * Actualiza los contadores de cupones
     *
     * @param total Total de cupones
     * @param redeemed Cupones canjeados
     * @param pending Cupones pendientes
     * @param expired Cupones expirados
     */
    public void updateCouponCounts(Integer total, Integer redeemed,
                                   Integer pending, Integer expired) {
        this.totalCoupons = total;
        this.redeemedCoupons = redeemed;
        this.pendingCoupons = pending;
        this.expiredCoupons = expired;
    }

    /**
     * Establece los detalles de la capa 2 (vista detallada)
     *
     * @param customerEmail Email del cliente
     * @param customerFirstName Primer nombre
     * @param customerLastName Apellido
     * @param paymentMethod Método de pago
     * @param paymentCode Código de pago
     * @param completedAt Fecha de completación
     * @param category Categoría de oferta
     * @param location Ubicación
     * @param imageUrl URL de imagen
     * @param offerValidTo Fecha de vencimiento
     */
    public void setDetailedInformation(String customerEmail, String customerFirstName,
                                       String customerLastName, PaymentMethod paymentMethod,
                                       String paymentCode, Date completedAt,
                                       String category, String location, String imageUrl,
                                       LocalDate offerValidTo, String paymentCodes) {
        this.customerEmail = customerEmail;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.paymentMethod = paymentMethod;
        this.paymentCode = paymentCode;
        this.completedAt = completedAt;
        this.category = category;
        this.location = location;
        this.imageUrl = imageUrl;
        this.offerValidTo = offerValidTo;
        this.paymentCodes = paymentCodes;
    }
}
