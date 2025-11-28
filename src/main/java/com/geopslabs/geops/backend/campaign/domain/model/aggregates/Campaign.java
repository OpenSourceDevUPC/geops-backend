package com.geopslabs.geops.backend.campaign.domain.model.aggregates;

import com.geopslabs.geops.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * Campaign entity for Campaign bounding context
 * It stores information about a Campaign in the web application
 * @see CampaignOffer
 */
@Entity
@Getter
public class Campaign extends AuditableAbstractAggregateRoot<Campaign> {

    @Column(nullable = false)
    private String campaignName;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private float estimatedBudget;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name= "campaign_id", nullable = false)
    private List<CampaignOffer> campaignOffers;

    public Campaign(){}


}
