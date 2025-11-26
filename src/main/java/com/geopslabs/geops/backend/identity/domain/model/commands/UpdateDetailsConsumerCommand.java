package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * UpdateDetailsConsumerCommand
 *
 * Command record for updating consumer details
 * This command allows partial updates - null fields will not be updated
 *
 * @summary Command to update consumer details
 * @param userId The unique identifier of the user
 * @param categoriasFavoritas Updated favorite categories (optional)
 * @param recibirNotificaciones Updated notification setting (optional)
 * @param permisoUbicacion Updated location permission (optional)
 * @param direccionCasa Updated home address (optional)
 * @param direccionTrabajo Updated work address (optional)
 * @param direccionUniversidad Updated university address (optional)
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record UpdateDetailsConsumerCommand(
    Long userId,
    String categoriasFavoritas,
    Boolean recibirNotificaciones,
    Boolean permisoUbicacion,
    String direccionCasa,
    String direccionTrabajo,
    String direccionUniversidad
) {
    /**
     * Compact constructor that validates the command parameters
     *
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateDetailsConsumerCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

