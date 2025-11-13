package com.geopslabs.geops.backend.coupons.interfaces.rest.transform;

import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateCouponCommand;
import com.geopslabs.geops.backend.coupons.domain.model.commands.CreateManyCouponsCommand;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CreateCouponResource;
import com.geopslabs.geops.backend.coupons.interfaces.rest.resources.CreateManyCouponsResource;

import java.util.List;

/**
 * CreateManyCouponsCommandFromResourceAssembler
 *
 * Assembler class responsible for converting CreateManyCouponsResource objects
 * to CreateManyCouponsCommand objects. This transformation follows the DDD pattern
 * of converting interface layer DTOs to domain layer commands for bulk operations.
 *
 * @summary Converts CreateManyCouponsResource to CreateManyCouponsCommand
 * @since 1.0
 * @author GeOps Labs
 */
public class CreateManyCouponsCommandFromResourceAssembler {

    /**
     * Converts a CreateManyCouponsResource to a CreateManyCouponsCommand.
     *
     * This method transforms the REST API bulk resource representation into
     * a domain command that can be processed by the domain services for
     * creating multiple coupons in a single operation.
     *
     * @param resource The CreateManyCouponsResource from the REST API request
     * @return A CreateManyCouponsCommand ready for domain processing
     * @throws IllegalArgumentException if the resource contains invalid data
     */
    public static CreateManyCouponsCommand toCommandFromResource(CreateManyCouponsResource resource) {
        List<CreateCouponCommand> couponCommands = resource.coupons().stream()
                .map(CreateCouponCommandFromResourceAssembler::toCommandFromResource)
                .toList();

        return new CreateManyCouponsCommand(couponCommands);
    }

    /**
     * Converts a list of CreateCouponResource to a CreateManyCouponsCommand.
     *
     * This method provides an alternative way to create a bulk command directly
     * from a list of individual coupon resources, which matches the frontend
     * expectation of sending an array of coupon resources to the bulk endpoint.
     *
     * @param resources List of CreateCouponResource from the REST API request
     * @return A CreateManyCouponsCommand ready for domain processing
     * @throws IllegalArgumentException if the resource list contains invalid data
     */
    public static CreateManyCouponsCommand toCommandFromResourceList(List<CreateCouponResource> resources) {
        if (resources == null || resources.isEmpty()) {
            throw new IllegalArgumentException("resources list cannot be null or empty");
        }

        List<CreateCouponCommand> couponCommands = resources.stream()
                .map(CreateCouponCommandFromResourceAssembler::toCommandFromResource)
                .toList();

        return new CreateManyCouponsCommand(couponCommands);
    }
}
