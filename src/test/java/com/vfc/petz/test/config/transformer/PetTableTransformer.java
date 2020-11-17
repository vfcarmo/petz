package com.vfc.petz.test.config.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.test.steps.EntitySampleFactory;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PetTableTransformer extends EntityTableTransformer<Pet> {

    public PetTableTransformer(ObjectMapper objectMapper, EntitySampleFactory entitySampleFactory) {
        super(objectMapper, entitySampleFactory);
    }

    @Override
    public Pet transform(Map<String, String> entry) throws Throwable {
        Pet pet = super.transform(entry);

        Optional.ofNullable(entry.get("ownerId"))
                .filter(this::isFilledVariable)
                .map(UUID::fromString)
                .ifPresent(ownerId -> {
                    Customer customer = new Customer();
                    customer.setId(ownerId);
                    pet.setOwner(customer);

                    Optional.ofNullable(entry.get("ownerName"))
                            .filter(this::isFilledVariable)
                            .ifPresent(customer::setName);

                    Optional.ofNullable(entry.get("ownerPhone"))
                            .filter(this::isFilledVariable)
                            .ifPresent(customer::setPhone);

                    Optional.ofNullable(entry.get("ownerEmail"))
                            .filter(this::isFilledVariable)
                            .ifPresent(customer::setEmail);
                });
        return pet;
    }
}
