package com.geopslabs.geops.backend.sales.domain.model.aggregates;

/**
 * Estados posibles de un pago en el sistema
 */
public enum PaymentStatus {
    /**
     * El pago está pendiente de ser procesado
     */
    PENDING,

    /**
     * El pago fue completado exitosamente
     */
    COMPLETED,

    /**
     * El pago falló en el proceso
     */
    FAILED
}


