package com.geopslabs.geops.backend.campaign.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.Date;

public record CampaignResource(Long id, Long userId, String name, String description, LocalDate startDate, LocalDate endDate,
                               String status, float estimatedBudget, Long totalImpressions, Long totalClicks,
                               float CTR, Date createdAt, Date updatedAt) {
}
