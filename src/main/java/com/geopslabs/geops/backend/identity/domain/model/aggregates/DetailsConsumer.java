package com.geopslabs.geops.backend.identity.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * DetailsConsumer Aggregate Root
 *
 * This aggregate represents consumer-specific details associated with a user.
 * It manages consumer preferences such as favorite categories, notification settings,
 * location permissions, and saved addresses (home, work, university).
 *
 * @summary Manages consumer profile information for users
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "details_consumer")
@Getter
public class DetailsConsumer extends AuditableAbstractAggregateRoot<DetailsConsumer> {

    /**
     * Reference to the user who owns this consumer profile
     * OneToOne relationship with User entity
     * The ID is shared with the User entity (usuario_id = user.id)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    /**
     * List of favorite categories (e.g., "Comida, Ropa, Electrónica")
     */
    @Column(name = "categorias_favoritas", length = 255)
    private String categoriasFavoritas;

    /**
     * Configuration to receive notifications (Yes/No)
     */
    @Column(name = "recibir_notificaciones", nullable = false)
    private Boolean recibirNotificaciones;

    /**
     * Permission to access location
     */
    @Column(name = "permiso_ubicacion", nullable = false)
    private Boolean permisoUbicacion;

    /**
     * Saved home address
     */
    @Column(name = "direccion_casa", length = 255)
    private String direccionCasa;

    /**
     * Saved work address
     */
    @Column(name = "direccion_trabajo", length = 255)
    private String direccionTrabajo;

    /**
     * Saved university address (or third custom location)
     */
    @Column(name = "direccion_universidad", length = 255)
    private String direccionUniversidad;

    /**
     * Default constructor for JPA
     */
    protected DetailsConsumer() {
    }

    /**
     * Creates a new DetailsConsumer
     *
     * @param user The user who owns this consumer profile
     * @param categoriasFavoritas List of favorite categories
     * @param recibirNotificaciones Configuration for notifications
     * @param permisoUbicacion Permission to access location
     * @param direccionCasa Home address
     * @param direccionTrabajo Work address
     * @param direccionUniversidad University address or custom location
     */
    public DetailsConsumer(User user, String categoriasFavoritas, Boolean recibirNotificaciones,
                          Boolean permisoUbicacion, String direccionCasa, String direccionTrabajo,
                          String direccionUniversidad) {
        this.user = user;
        this.categoriasFavoritas = categoriasFavoritas;
        this.recibirNotificaciones = recibirNotificaciones != null ? recibirNotificaciones : false;
        this.permisoUbicacion = permisoUbicacion != null ? permisoUbicacion : false;
        this.direccionCasa = direccionCasa;
        this.direccionTrabajo = direccionTrabajo;
        this.direccionUniversidad = direccionUniversidad;
    }

    /**
     * Updates consumer profile information
     *
     * @param categoriasFavoritas Updated favorite categories
     * @param recibirNotificaciones Updated notification setting
     * @param permisoUbicacion Updated location permission
     * @param direccionCasa Updated home address
     * @param direccionTrabajo Updated work address
     * @param direccionUniversidad Updated university address
     */
    public void updateConsumerDetails(String categoriasFavoritas, Boolean recibirNotificaciones,
                                     Boolean permisoUbicacion, String direccionCasa,
                                     String direccionTrabajo, String direccionUniversidad) {
        if (categoriasFavoritas != null) {
            this.categoriasFavoritas = categoriasFavoritas;
        }
        if (recibirNotificaciones != null) {
            this.recibirNotificaciones = recibirNotificaciones;
        }
        if (permisoUbicacion != null) {
            this.permisoUbicacion = permisoUbicacion;
        }
        if (direccionCasa != null) {
            this.direccionCasa = direccionCasa;
        }
        if (direccionTrabajo != null) {
            this.direccionTrabajo = direccionTrabajo;
        }
        if (direccionUniversidad != null) {
            this.direccionUniversidad = direccionUniversidad;
        }
    }
}

