package com.geopslabs.geops.backend.sales.interfaces.dto;

import com.geopslabs.geops.backend.sales.domain.model.aggregates.OrderHistory;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir OrderHistory a DTOs
 *
 * Encapsula la lógica de transformación de la entidad
 * a objetos de transferencia de datos (DTO)
 */
@Component
public class OrderHistoryDTOMapper {

    /**
     * Convierte OrderHistory a OrderHistoryGeneralDTO (Vista General - Tabla)
     *
     * Solo incluye campos necesarios para la tabla/lista
     *
     * @param orderHistory OrderHistory a convertir
     * @return OrderHistoryGeneralDTO con información resumida
     */
    public OrderHistoryGeneralDTO toGeneralDTO(OrderHistory orderHistory) {
        if (orderHistory == null) {
            return null;
        }

        OrderHistoryGeneralDTO dto = new OrderHistoryGeneralDTO();
        dto.setId(orderHistory.getId());
        dto.setPaymentId(orderHistory.getPaymentId());
        dto.setCustomerName(orderHistory.getCustomerName());
        dto.setOfferTitle(orderHistory.getOfferTitle());
        dto.setPartnerName(orderHistory.getPartnerName());
        dto.setPurchasePrice(orderHistory.getPurchasePrice());
        dto.setPurchaseDate(orderHistory.getPurchaseDate());
        dto.setPaymentStatus(orderHistory.getPaymentStatus().toString());
        dto.setTotalCoupons(orderHistory.getTotalCoupons());
        dto.setRedeemedCoupons(orderHistory.getRedeemedCoupons());
        dto.setPendingCoupons(orderHistory.getPendingCoupons());
        dto.setExpiredCoupons(orderHistory.getExpiredCoupons());

        // Generar resumen de estado
        dto.setCouponStatus(generateCouponStatus(
                orderHistory.getTotalCoupons(),
                orderHistory.getRedeemedCoupons(),
                orderHistory.getExpiredCoupons()
        ));

        return dto;
    }

    /**
     * Convierte OrderHistory a OrderHistoryDetailedDTO (Vista Detallada)
     *
     * Incluye TODOS los detalles del cliente, pago y oferta
     *
     * @param orderHistory OrderHistory a convertir
     * @return OrderHistoryDetailedDTO con información completa
     */
    public OrderHistoryDetailedDTO toDetailedDTO(OrderHistory orderHistory) {
        if (orderHistory == null) {
            return null;
        }

        OrderHistoryDetailedDTO dto = new OrderHistoryDetailedDTO();

        // IDs principales
        dto.setId(orderHistory.getId());
        dto.setPaymentId(orderHistory.getPaymentId());
        dto.setUserId(orderHistory.getUserId());
        dto.setSupplierId(orderHistory.getSupplierId());
        dto.setOfferId(orderHistory.getOfferId());
        dto.setCartId(orderHistory.getCartId());

        // Información cliente
        dto.setCustomerName(orderHistory.getCustomerName());
        dto.setCustomerEmail(orderHistory.getCustomerEmail());
        dto.setCustomerFirstName(orderHistory.getCustomerFirstName());
        dto.setCustomerLastName(orderHistory.getCustomerLastName());

        // Información pago
        dto.setPurchasePrice(orderHistory.getPurchasePrice());
        dto.setPaymentMethod(orderHistory.getPaymentMethod() != null ?
                orderHistory.getPaymentMethod().toString() : null);
        dto.setPaymentCode(orderHistory.getPaymentCode());
        dto.setPaymentStatus(orderHistory.getPaymentStatus().toString());
        dto.setPurchaseDate(orderHistory.getPurchaseDate());
        dto.setCompletedAt(orderHistory.getCompletedAt());
        dto.setPaymentCodes(orderHistory.getPaymentCodes());

        // Información oferta
        dto.setOfferTitle(orderHistory.getOfferTitle());
        dto.setPartnerName(orderHistory.getPartnerName());
        dto.setCategory(orderHistory.getCategory());
        dto.setLocation(orderHistory.getLocation());
        dto.setImageUrl(orderHistory.getImageUrl());
        dto.setOfferValidTo(orderHistory.getOfferValidTo());

        // Información cupones
        dto.setTotalCoupons(orderHistory.getTotalCoupons());
        dto.setRedeemedCoupons(orderHistory.getRedeemedCoupons());
        dto.setPendingCoupons(orderHistory.getPendingCoupons());
        dto.setExpiredCoupons(orderHistory.getExpiredCoupons());
        dto.setCouponStatus(generateCouponStatus(
                orderHistory.getTotalCoupons(),
                orderHistory.getRedeemedCoupons(),
                orderHistory.getExpiredCoupons()
        ));

        // Auditoría
        dto.setCreatedAt(orderHistory.getCreatedAt());
        dto.setUpdatedAt(orderHistory.getUpdatedAt());

        return dto;
    }

    /**
     * Genera un resumen visual del estado de canje
     *
     * Ejemplos:
     * - "5 de 5 ✅" (todos canjeados)
     * - "2 de 5 ⏳" (algunos pendientes)
     * - "1 de 5, 1 expirado" (con expirados)
     *
     * @param total Total de cupones
     * @param redeemed Cupones canjeados
     * @param expired Cupones expirados
     * @return String con resumen del estado
     */
    private String generateCouponStatus(Integer total, Integer redeemed, Integer expired) {
        if (total == null || total == 0) {
            return "Sin cupones";
        }

        // Si todos están canjeados
        if (redeemed != null && redeemed.equals(total)) {
            return redeemed + " de " + total + " ✅";
        }

        // Si algunos están canjeados
        if (redeemed != null && redeemed > 0) {
            return redeemed + " de " + total + " ⏳";
        }

        // Si ninguno está canjeado pero algunos expiraron
        if (expired != null && expired > 0) {
            return "0 canjeados, " + expired + " expirados";
        }

        // Si ninguno está canjeado
        return "0 de " + total;
    }
}
