package com.vfc.petz.test.config.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.test.steps.EntitySampleFactory;

import java.util.Map;

public class PetTableTransformer extends EntityTableTransformer<Pet> {

    public PetTableTransformer(ObjectMapper objectMapper, EntitySampleFactory entitySampleFactory) {
        super(objectMapper, entitySampleFactory);
    }

    @Override
    public Pet transform(Map<String, String> entry) throws Throwable {
        return super.transform(entry);
    }
}
