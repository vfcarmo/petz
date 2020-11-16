package com.vfc.petz.test.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.domain.repository.PetRepository;
import com.vfc.petz.test.ApiPetzTestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

public class PetSteps {

    private final ApiPetzTestData testData;
    private final PetRepository petRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final EntitySampleFactory entitySampleFactory;

    @Autowired
    public PetSteps(ApiPetzTestData testData, PetRepository petRepository, MockMvc mockMvc, ObjectMapper objectMapper,
                    EntitySampleFactory entitySampleFactory) {
        this.testData = testData;
        this.petRepository = petRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.entitySampleFactory = entitySampleFactory;
    }

    @Before
    public void setUp() {
        this.testData.reset();
    }

    @After
    public void wrapUp() {
//        Mockito.reset(petRepository);
    }

    @Given("database contains pets as:")
    public void databaseContainsPetsAs(List<Pet> pets) {

    }
}
