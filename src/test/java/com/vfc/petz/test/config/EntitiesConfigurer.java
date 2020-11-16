package com.vfc.petz.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.test.config.transformer.CustomerTableTransformer;
import com.vfc.petz.test.config.transformer.PetTableTransformer;
import com.vfc.petz.test.steps.EntitySampleFactory;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;

public class EntitiesConfigurer implements TypeRegistryConfigurer {

    public EntitiesConfigurer() {
    }

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        EntitySampleFactory sampleFactory = new EntitySampleFactory();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        typeRegistry.defineDataTableType(
                new DataTableType(Customer.class, new CustomerTableTransformer(objectMapper, sampleFactory)));

        typeRegistry.defineDataTableType(
                new DataTableType(Pet.class, new PetTableTransformer(objectMapper, sampleFactory)));
    }
}
