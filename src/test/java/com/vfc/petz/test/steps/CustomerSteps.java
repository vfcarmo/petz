package com.vfc.petz.test.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.dto.CustomerRequest;
import com.vfc.petz.domain.dto.CustomerResponse;
import com.vfc.petz.domain.dto.CustomerUpdateRequest;
import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.test.ApiPetzTestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class CustomerSteps {

    private final ApiPetzTestData testData;
    private final CustomerRepository customerRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerSteps(ApiPetzTestData testData, CustomerRepository customerRepository, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.testData = testData;
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

    }

    @Given("database contains customers as:")
    public void databaseContainsCustomersAs(List<Customer> customers) {
        given(customerRepository.findByIdAndStatusIsNot(any(UUID.class), any(EntityStatus.class)))
                .willReturn(Optional.empty());
        customers.forEach(customer -> {
            given(customerRepository.findByIdAndStatusIsNot(eq(customer.getId()), eq(EntityStatus.EXCLUDED)))
                    .willReturn(Optional.of(customer));
        });
        given(customerRepository.findByNameAndStatusIsNot(any(), any(EntityStatus.class), any(Pageable.class)))
                .willAnswer((Answer<Page<Customer>>) invocation -> {
                    String name = invocation.getArgument(0);
                    EntityStatus status = invocation.getArgument(1);
                    Pageable pageable = invocation.getArgument(2);

                    List<Customer> customerList = Optional.ofNullable(name)
                            .filter(StringUtils::isNoneBlank)
                            .map(n -> customers.stream()
                                    .filter(c -> c.getName().toUpperCase().startsWith(n.toUpperCase()) && c.getStatus() != status)
                                    .collect(Collectors.toList()))
                            .orElse(customers.stream()
                                    .filter(c -> c.getStatus() != status)
                                    .collect(Collectors.toList()));

                    return new PageImpl<>(customerList, pageable, customerList.size());
                });
    }

    @Given("the next customer creation data is:")
    public void theNextCustomerCreationDataIs(Map<String, String> data) {
        LocalDateTime now = LocalDateTime.parse(data.get("now"), DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss z"));
        this.testData.setNow(now);
        given(customerRepository.save(any(Customer.class))).willAnswer((Answer<Customer>) invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.setId(UUID.fromString(data.get("nextId")));
            customer.setCreationDate(now);
            customer.setLastUpdate(now);

            return customer;
        });
    }

    @Given("the user prepare a request to create a customer with details as:")
    public void theUserPrepareARequestToCreateACustomerWithDetailsAs(Map<String, String> data) {
        String name = data.get("name");
        String cpf = data.get("cpf");
        String phone = data.get("phone");
        String email = data.get("email");

        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(name)
                .cpf(cpf)
                .phone(phone)
                .email(email)
                .build();

        this.testData.setRequestJson(toJson(objectMapper, customerRequest));
    }

    @When("the user request a creation to this customer")
    public void theUserRequestACreationToThisCustomer() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/customers")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.testData.getRequestJson());

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request customers:")
    public void theUserRequestCustomers(Map<String, String> parameters) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/customers")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        parameters.forEach(mockHttpServletRequestBuilder::queryParam);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }


    @When("the user request details about the customer {string}")
    public void theUserRequestDetailsAboutTheCustomerCustomerId(String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request an update of the customer {string} as follows:")
    public void theUserRequestAnUpdateOfTheCustomerIdAsFollows(String customerId, Map<String, String> data) throws Exception {
        CustomerUpdateRequest.CustomerUpdateRequestBuilder builder = CustomerUpdateRequest.builder();
        Optional.of(data.get("name"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(builder::name);

        Optional.of(data.get("phone"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(builder::phone);

        Optional.of(data.get("email"))
                .filter(StringUtils::isNotBlank)
                .ifPresent(builder::email);

        CustomerUpdateRequest customerUpdateRequest = builder.build();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.put("/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(objectMapper, customerUpdateRequest));

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request deletion of the customer {string}")
    public void theUserRequestDeletionOfTheCustomerCustomerId(String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete("/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request activation of the customer {string}")
    public void theUserRequestActivationOfTheCustomerCustomerId(String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.patch("/customers/" + customerId + "/activate")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("the user request inactivation of the customer {string}")
    public void theUserRequestInactivationOfTheCustomerCustomerId(String customerId) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.patch("/customers/" + customerId + "/inactivate")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @Then("the database is called to insert this customer:")
    public void theDatabaseIsCalledToInsertThisCustomer(Customer expectedCustomer) {
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should(times(1))
                .save(customerArgumentCaptor.capture());

        Customer actualCustomer = customerArgumentCaptor.getValue();

        Assert.assertEquals(expectedCustomer, actualCustomer);
    }

    @Then("the service will reply this list of customers: {string}")
    public void theServiceWillReplyThisListOfCustomersResponse(String expectedJson) throws Exception {
        TypeReference<PageResponse<CustomerResponse>> typeReference = new TypeReference<>() {
        };
        PageResponse<CustomerResponse> expectedResponse = objectMapper.readValue(expectedJson, typeReference);

        MvcResult mvcResult = this.testData.getResultActions().andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString();

        PageResponse<CustomerResponse> actualResponse = objectMapper.readValue(actualJson, typeReference);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Then("the service will reply this customer: {string}")
    public void theServiceWillReplyThisCustomerResponse(String expectedJson) throws Exception {
        CustomerResponse expectedResponse = toObject(objectMapper, expectedJson, CustomerResponse.class);

        MvcResult mvcResult = this.testData.getResultActions().andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();
        CustomerResponse actualResponse = toObject(objectMapper, actualJson, CustomerResponse.class);

        Assert.assertEquals(expectedResponse, actualResponse);
    }

}
