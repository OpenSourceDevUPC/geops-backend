package com.geopslabs.geops.backend.identity.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * User Aggregate Root
 *
 * This aggregate represents a user in the GeOps platform.
 * It manages user identity information including credentials,
 * role, and subscription plan
 *
 * @summary Manages user identity and authentication
 * @since 1.0
 * @author GeOps Labs
 */
@Entity
@Table(name = "users")
@Getter
public class User extends AuditableAbstractAggregateRoot<User> {

    /**
     * Full name of the user
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Email address of the user (unique identifier)
     */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Encrypted password for authentication
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Role of the user in the system (e.g., CONSUMER, ADMIN)
     */
    @Column(name = "role", nullable = false, length = 50)
    private String role;

    /**
     * Subscription plan of the user (e.g., BASIC, PREMIUM)
     */
    @Column(name = "plan", nullable = false, length = 50)
    private String plan;

    /**
     * Phone number of the user (optional)
     */
    @Column(name = "phone", length = 20)
    private String phone;

    /**
     * Default constructor for JPA
     */
    protected User() {
    }

    /**
     * Creates a new User
     *
     * @param name The user's full name
     * @param email The user's email address
     * @param password The user's encrypted password
     * @param phone The user's phone number
     * @param role The user's role
     * @param plan The user's subscription plan
     */
    public User(String name, String email, String password, String phone, String role, String plan) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.plan = plan;
    }

    /**
     * Updates user information
     *
     * @param name The updated name
     * @param email The updated email
     * @param role The updated role
     * @param plan The updated plan
     */
    public void updateUser(String name, String email, String role, String plan) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
        if (role != null && !role.isBlank()) {
            this.role = role;
        }
        if (plan != null && !plan.isBlank()) {
            this.plan = plan;
        }
    }

    /**
     * Updates user password
     *
     * @param encryptedPassword The new encrypted password
     */
    public void updatePassword(String encryptedPassword) {
        if (encryptedPassword != null && !encryptedPassword.isBlank()) {
            this.password = encryptedPassword;
        }
    }

    /**
     * Checks if the user has a premium plan
     *
     * @return true if the user's plan is premium, false otherwise
     */
    public boolean isPremium() {
        return "PREMIUM".equalsIgnoreCase(this.plan);
    }

    /**
     * Checks if the user is an admin
     *
     * @return true if the user's role is admin, false otherwise
     */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }
}

