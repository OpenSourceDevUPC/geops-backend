package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * DetailsConsumerResource
 *
 * Resource Resource for consumer details responses via REST API
 * This resource represents the response payload containing consumer details information
 *
 * @summary Response resource for consumer details data
 * @param id The unique identifier (same as user ID)
 * @param userId The user ID associated with these consumer details
 * @param categoriasFavoritas List of favorite categories
 * @param recibirNotificaciones Configuration to receive notifications
 * @param permisoUbicacion Permission to access location
 * @param direccionCasa Saved home address
 * @param direccionTrabajo Saved work address
 * @param direccionUniversidad Saved university address
 * @param createdAt Timestamp when the consumer details were created
 * @param updatedAt Timestamp when the consumer details were last updated
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record DetailsConsumerResource(
    Long id,
    Long userId,
    String categoriasFavoritas,
    Boolean recibirNotificaciones,
    Boolean permisoUbicacion,
    String direccionCasa,
    String direccionTrabajo,
    String direccionUniversidad,
    java.util.Date createdAt,
    java.util.Date updatedAt
) {
}

