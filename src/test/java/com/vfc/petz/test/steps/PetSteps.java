package com.vfc.petz.test.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.dto.PetRequest;
import com.vfc.petz.domain.dto.PetResponse;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.domain.entity.PetType;
import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.domain.repository.PetRepository;
import com.vfc.petz.test.ApiPetzTestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.vfc.petz.test.steps.EntitySampleFactory.toJson;
import static com.vfc.petz.test.steps.EntitySampleFactory.toObject;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class PetSteps {

    private final ApiPetzTestData testData;
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public PetSteps(ApiPetzTestData testData, PetRepository petRepository, CustomerRepository customerRepository,
                    MockMvc mockMvc, ObjectMapper objectMapper) {
        this.testData = testData;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Before
    public void setUp() {
        this.testData.reset();
    }

    @After
    public void wrapUp() {
        Mockito.reset(petRepository, customerRepository);
    }

    @Given("database contains pets as:")
    public void databaseContainsPetsAs(List<Pet> pets) {
        pets.forEach(pet -> {
            given(petRepository.findByNameAndOwnerIdAndStatusIsNot(anyString(), any(UUID.class),
                    eq(EntityStatus.EXCLUDED), any(Pageable.class)))
                    .willAnswer((Answer<Page<Pet>>) invocation -> {
                        String name = invocation.getArgument(0);
                        Pageable pageable = invocation.getArgument(3);

                        List<Pet> petList = Optional.ofNullable(name)
                                .filter(StringUtils::isNoneBlank)
                                .map(n -> pets.stream()
                                        .filter(c -> c.getName().toUpperCase().startsWith(n.toUpperCase()) && c.getStatus() != EntityStatus.EXCLUDED)
                                        .collect(Collectors.toList()))
                                .orElse(pets.stream()
                                        .filter(c -> c.getStatus() != EntityStatus.EXCLUDED)
                                        .collect(Collectors.toList()));

                        return new PageImpl<>(petList, pageable, petList.size());
                    });
            given(customerRepository.findByIdAndStatusIsNot(eq(pet.getOwner().getId()), eq(EntityStatus.EXCLUDED)))
                    .willReturn(Optional.of(pet.getOwner()));
            given(petRepository.findByIdAndStatusIsNot(eq(pet.getId()), eq(EntityStatus.EXCLUDED)))
                    .willReturn(Optional.of(pet));
        });
    }

    @Given("the next pet creation data is:")
    public void theNextPetCreationDataIs(Map<String, String> data) {
        LocalDateTime now = LocalDateTime.parse(data.get("now"), DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss z"));
        this.testData.setNow(now);
        given(petRepository.save(any(Pet.class))).willAnswer((Answer<Pet>) invocation -> {
            Pet pet = invocation.getArgument(0);
            pet.setId(UUID.fromString(data.get("nextId")));
            pet.setCreationDate(now);
            pet.setLastUpdate(now);

            return pet;
        });
    }

    @When("the user request pets of the customer {string}:")
    public void theUserRequestPets(String customerId, Map<String, String> parameters) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/customers/" + customerId + "/pets")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        parameters.forEach(mockHttpServletRequestBuilder::queryParam);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request details about the pet {string} of the customer {string}")
    public void theUserRequestDetailsAboutThePetPetIdOfTheCustomerCustomerId(String petId, String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/customers/" + customerId + "/pets/" + petId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request to add a pet to customer {string} as follows:")
    public void theUserRequestToAddAPetToCustomerOwnerIdAsFollows(String customerId, Map<String, String> data) throws Exception {
        PetRequest.PetRequestBuilder builder = PetRequest.builder();

        Optional.ofNullable(data.get("name"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(builder::name);

        Optional.ofNullable(data.get("type"))
                .filter(StringUtils::isNotBlank)
                .map(PetType::valueOf)
                .ifPresent(builder::type);

        Optional.ofNullable(data.get("observation"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(builder::observation);

        Optional.ofNullable(data.get("birthdate"))
                .filter(StringUtils::isNotBlank)
                .map(LocalDateTime::parse)
                .ifPresent(builder::birthdate);

        PetRequest petRequest = builder.build();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/customers/" + customerId + "/pets")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(objectMapper, petRequest));

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request to deletion of the pet {string} of the customer {string}")
    public void theUserRequestToDeletionOfThePetIdOfTheCustomerOwnerId(String petId, String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete("/customers/" + customerId + "/pets/" + petId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @Then("the service will reply this list of pets: {string}")
    public void theServiceWillReplyThisListOfPetsResponse(String expectedJson) throws Exception {
        TypeReference<PageResponse<PetResponse>> typeReference = new TypeReference<>() {
        };
        PageResponse<PetResponse> expectedResponse = objectMapper.readValue(expectedJson, typeReference);

        MvcResult mvcResult = this.testData.getResultActions().andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString();

        PageResponse<PetResponse> actualResponse = objectMapper.readValue(actualJson, typeReference);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Then("the service will reply this pet: {string}")
    public void theServiceWillReplyThisPetResponse(String expectedJson) throws Exception {
        PetResponse expectedResponse = toObject(objectMapper, expectedJson, PetResponse.class);

        MvcResult mvcResult = this.testData.getResultActions().andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString();

        PetResponse actualResponse = toObject(objectMapper, actualJson, PetResponse.class);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

}
