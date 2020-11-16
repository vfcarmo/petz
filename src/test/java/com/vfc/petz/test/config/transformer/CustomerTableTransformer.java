package com.vfc.petz.test.config.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.test.steps.EntitySampleFactory;

import java.util.Map;

public class CustomerTableTransformer extends EntityTableTransformer<Customer> {

    public CustomerTableTransformer(ObjectMapper objectMapper, EntitySampleFactory entitySampleFactory) {
        super(objectMapper, entitySampleFactory);
    }

    @Override
    public Customer transform(Map<String, String> entry) throws Throwable {

        Customer customer = super.transform(entry);



        return customer;
    }
}
