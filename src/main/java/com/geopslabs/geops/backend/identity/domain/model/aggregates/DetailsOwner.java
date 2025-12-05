package com.geopslabs.geops.backend.identity.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * DetailsOwner Aggregate Root
 *
 * This aggregate represents owner-specific details associated with a user.
 * It manages business information such as business name, type, tax ID,
 * website, description, address, and operating hours.
 *
 * @summary Manages owner/business profile information for users
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "details_owner")
@Getter
public class DetailsOwner extends AuditableAbstractAggregateRoot<DetailsOwner> {

    /**
     * Reference to the user who owns this owner profile
     * OneToOne relationship with User entity
     * The ID is shared with the User entity (usuario_id = user.id)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    /**
     * Business name of the owner
     */
    @Column(name = "business_name", nullable = false)
    private String businessName;

    /**
     * Type of business (e.g., Restaurante, Tienda, Servicio)
     */
    @Column(name = "business_type", length = 100)
    private String businessType;

    /**
     * Tax identification number (CUIT/RUC/NIT)
     */
    @Column(name = "tax_id", length = 50)
    private String taxId;

    /**
     * Business website URL
     */
    @Column(name = "website")
    private String website;

    /**
     * Detailed business description
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Main physical address of the business
     */
    @Column(name = "address")
    private String address;

    /**
     * Business operating hours/schedule
     */
    @Column(name = "horario_atencion")
    private String horarioAtencion;

    /**
     * Default constructor for JPA
     */
    protected DetailsOwner() {
    }

    /**
     * Creates a new DetailsOwner
     *
     * @param user The user who owns this owner profile
     * @param businessName The business name
     * @param businessType The type of business
     * @param taxId The tax identification number
     * @param website The business website
     * @param description The business description
     * @param address The business address
     * @param horarioAtencion The operating hours
     */
    public DetailsOwner(User user, String businessName, String businessType, String taxId,
                          String website, String description, String address, String horarioAtencion) {
        this.user = user;
        this.businessName = businessName;
        this.businessType = businessType;
        this.taxId = taxId;
        this.website = website;
        this.description = description;
        this.address = address;
        this.horarioAtencion = horarioAtencion;
    }

    /**
     * Updates owner profile information
     *
     * @param businessName Updated business name
     * @param businessType Updated business type
     * @param taxId Updated tax ID
     * @param website Updated website
     * @param description Updated description
     * @param address Updated address
     * @param horarioAtencion Updated operating hours
     */
    public void updateOwnerDetails(String businessName, String businessType, String taxId,
                                     String website, String description, String address,
                                     String horarioAtencion) {
        if (businessName != null && !businessName.isBlank()) {
            this.businessName = businessName;
        }
        if (businessType != null) {
            this.businessType = businessType;
        }
        if (taxId != null) {
            this.taxId = taxId;
        }
        if (website != null) {
            this.website = website;
        }
        if (description != null) {
            this.description = description;
        }
        if (address != null) {
            this.address = address;
        }
        if (horarioAtencion != null) {
            this.horarioAtencion = horarioAtencion;
        }
    }
}

