package com.geopslabs.geops.backend.campaign.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateCampaignResource(String name, String description, LocalDate startDate,
                                     LocalDate endDate, String status, Float estimatedBudget) {
}
