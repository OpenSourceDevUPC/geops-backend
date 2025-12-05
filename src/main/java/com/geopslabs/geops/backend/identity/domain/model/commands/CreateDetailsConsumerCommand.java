package com.geopslabs.geops.backend.identity.domain.model.commands;

/**
 * CreateDetailsConsumerCommand
 *
 * Command record for creating consumer details for a user
 *
 * @summary Command to create consumer details
 * @param userId The unique identifier of the user
 * @param categoriasFavoritas List of favorite categories
 * @param recibirNotificaciones Configuration for notifications
 * @param permisoUbicacion Permission to access location
 * @param direccionCasa Home address
 * @param direccionTrabajo Work address
 * @param direccionUniversidad University address or custom location
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateDetailsConsumerCommand(
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
    public CreateDetailsConsumerCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
    }
}

