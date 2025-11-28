package com.geopslabs.geops.backend.campaign.domain.model.aggregates;

import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.CampaignOfferId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

/**
 * CampaignOffer class to store information about the Offer with additional information
 * {@link Campaign}
 */
@Entity
@Getter
public class CampaignOffer {

    @EmbeddedId
    private CampaignOfferId id;

    @Column(nullable = false)
    private Long offerImpressions;

    @Column(nullable = false)
    private Long offerClicks;

    @Column(nullable = false)
    private float offerCTR;

    public CampaignOffer() {}

    public CampaignOffer(CampaignOfferId id) {
        this.id = id;
    }

    //Function to calculate the CTR using offerImpressions and offerClicks values
    public float calculateCTR()
    {
        return (float) (this.offerClicks * 100) / this.offerImpressions;
    }

}
