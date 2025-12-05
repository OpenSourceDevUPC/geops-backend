package com.geopslabs.geops.backend.identity.interfaces.rest.resources;

/**
 * CreateDetailsConsumerResource
 *
 * Resource Resource for creating consumer details via REST API
 * This resource represents the request payload for creating new consumer details
 *
 * @summary Request resource for creating consumer details
 * @param categoriasFavoritas List of favorite categories
 * @param recibirNotificaciones Configuration to receive notifications
 * @param permisoUbicacion Permission to access location
 * @param direccionCasa Saved home address
 * @param direccionTrabajo Saved work address
 * @param direccionUniversidad Saved university address
 *
 * @since 1.0
 * @author GeOps Labs
 */
public record CreateDetailsConsumerResource(
    String categoriasFavoritas,
    Boolean recibirNotificaciones,
    Boolean permisoUbicacion,
    String direccionCasa,
    String direccionTrabajo,
    String direccionUniversidad
) {
}

