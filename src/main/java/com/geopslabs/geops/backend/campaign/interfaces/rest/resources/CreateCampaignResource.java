package com.geopslabs.geops.backend.campaign.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCampaignResource(Long userId, String name, String description, LocalDate startDate, LocalDate endDate) {
}
