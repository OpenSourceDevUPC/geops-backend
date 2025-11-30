package com.geopslabs.geops.backend.campaign.domain.model.aggregates;

import com.geopslabs.geops.backend.campaign.domain.model.commands.CreateCampaignCommand;
import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.ECampaignStatus;
import com.geopslabs.geops.backend.identity.domain.model.aggregates.User;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Campaign entity for Campaign bounding context
 * It stores information about a Campaign in the web application
 */
@Entity
@Getter
public class Campaign extends AuditableAbstractAggregateRoot<Campaign> {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name= "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ECampaignStatus status;

    @Column(name = "estimated_budget", nullable = false)
    private float estimatedBudget;

    @Column(name = "total_impressions", nullable = false)
    private Long totalImpressions;

    @Column(name = "total_clicks", nullable = false)
    private Long totalClicks;

    @Column(name ="CTR", nullable = false)
    private float CTR;

    public Campaign(){}

    public Long getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    public Campaign(User user, CreateCampaignCommand command) {
        this.user = user;
        this.name = command.name();
        this.description = command.description();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
        this.status = ECampaignStatus.ACTIVE;
        this.estimatedBudget = 0;
        this.totalImpressions = 0L;
        this.totalClicks = 0L;
        this.CTR = 0;
    }

    public void edit(String name, String description, LocalDate startDate, LocalDate endDate, ECampaignStatus status) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
