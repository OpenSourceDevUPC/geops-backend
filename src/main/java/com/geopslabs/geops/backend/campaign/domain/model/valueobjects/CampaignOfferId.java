package com.geopslabs.geops.backend.campaign.domain.model.valueobjects;

import com.geopslabs.geops.backend.campaign.domain.model.aggregates.CampaignOffer;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Campaign Offer Id class for CampaignOffer entity.
 * It works as a composite key for {@link CampaignOffer} class
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class CampaignOfferId implements Serializable {

    //Primary and foreign key
    private Long campaignId;

    //Primary and foreign key
    private Long offerId;

    public CampaignOfferId() {}

    public CampaignOfferId(Long campaignId, Long offerId) {
        this.campaignId = campaignId;
        this.offerId = offerId;
    }

}
