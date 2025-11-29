package com.geopslabs.geops.backend.campaign.domain.model.aggregates;

import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.ECampaignStatus;
import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Campaign entity for Campaign bounding context
 * It stores information about a Campaign in the web application
 */
@Entity
@Getter
public class Campaign extends AuditableAbstractAggregateRoot<Campaign> {

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


}
