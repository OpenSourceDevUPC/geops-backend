package com.geopslabs.geops.backend.sales.domain.model.aggregates;

/**
 * Métodos de pago disponibles en el sistema
 */
public enum PaymentMethod {
    /**
     * Pago a través de Yape (billetera digital)
     */
    YAPE,

    /**
     * Pago con tarjeta de crédito/débito
     */
    CARD,

    /**
     * Pago a través de transferencia bancaria
     */
    BANK_TRANSFER
}

