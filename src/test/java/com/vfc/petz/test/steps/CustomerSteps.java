package com.vfc.petz.test.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.dto.CustomerRequest;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.domain.repository.PetRepository;
import com.vfc.petz.test.ApiPetzTestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class CustomerSteps {

    private final ApiPetzTestData testData;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final EntitySampleFactory entitySampleFactory;

    @Autowired
    public CustomerSteps(ApiPetzTestData testData, CustomerRepository customerRepository, PetRepository petRepository, MockMvc mockMvc,
                         ObjectMapper objectMapper, EntitySampleFactory entitySampleFactory) {
        this.testData = testData;
        this.customerRepository = customerRepository;
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

    }

    @Given("database contains customers as:")
    public void databaseContainsCustomersAs(List<Customer> customers) {
        customers.forEach(customer -> {
            given(customerRepository.findByNameAndStatusIsNot(anyString(), eq(EntityStatus.ACTIVE), any(Pageable.class)))
                    .willAnswer((Answer<Page<Customer>>) invocation -> {
                        String name = invocation.getArgument(0);
                        Pageable pageable = invocation.getArgument(2);

                        List<Customer> customerList = Optional.ofNullable(name)
                                .map(n -> customers.stream()
                                        .filter(c -> c.getName().equals(n) && c.getStatus() != EntityStatus.EXCLUDED)
                                        .collect(Collectors.toList()))
                                .orElse(customers.stream()
                                        .filter(c -> c.getStatus() != EntityStatus.EXCLUDED)
                                        .collect(Collectors.toList()));

                        return new PageImpl<>(customerList, pageable, customerList.size());
                    });
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

    @Then("the database is called to insert this customer:")
    public void theDatabaseIsCalledToInsertThisCustomer(Customer expectedCustomer) {
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        then(customerRepository).should(times(1))
                .save(customerArgumentCaptor.capture());

        Customer actualCustomer = customerArgumentCaptor.getValue();

        Assert.assertEquals(expectedCustomer, actualCustomer);
    }

}
