package com.geopslabs.geops.backend.campaign.domain.model.commands;

import com.geopslabs.geops.backend.campaign.domain.model.valueobjects.ECampaignStatus;

import java.time.LocalDate;
import java.util.Arrays;

public record UpdateCampaignCommand(Long id, String name, String description, LocalDate startDate,
                                    LocalDate endDate, String status, Float estimatedBudget) {
    public UpdateCampaignCommand {

        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");

        if(description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or blank");

        if(startDate == null)
            throw new IllegalArgumentException("Start date cannot be null");

        if(endDate == null)
            throw new IllegalArgumentException("End date cannot be null");

        if(endDate.isBefore(startDate))
            throw new IllegalArgumentException("End date cannot be before start date");

        if(status == null || status.isBlank())
            throw new IllegalArgumentException("Status cannot be null or blank");

        try {
            ECampaignStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid status, allowed values: " + Arrays.toString(ECampaignStatus.values())
            );
        }
    }
}
