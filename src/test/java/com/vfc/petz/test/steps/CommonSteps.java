package com.vfc.petz.test.steps;

import com.vfc.petz.PetzApplication;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.entity.Pet;
import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.domain.repository.PetRepository;
import com.vfc.petz.test.ApiPetzTestData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {PetzApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommonSteps {

    private final ApiPetzTestData testData;
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    @Autowired
    public CommonSteps(ApiPetzTestData testData, CustomerRepository customerRepository, PetRepository petRepository) {
        this.testData = testData;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Before
    public void setUp() {
        this.testData.reset();
    }

    @After
    public void wrapUp() {
        reset(customerRepository, petRepository);
    }

    @Given("the database is {string}")
    public void theDatabaseIs(String status) {
        if (!"online".equals(status)) {
            setCustomerRepositoryOffline();
            setPetRepositoryOffline();
        }
    }

    private void setCustomerRepositoryOffline() {
        given(customerRepository.findByNameAndStatusIsNot(any(), any(EntityStatus.class), any(Pageable.class)))
                .willThrow(RuntimeException.class);
        given(customerRepository.findByIdAndStatusIsNot(any(UUID.class), any(EntityStatus.class)))
                .willThrow(RuntimeException.class);
        given(customerRepository.save(any(Customer.class)))
                .willThrow(RuntimeException.class);
        willThrow(RuntimeException.class)
                .given(customerRepository)
                .deleteById(any(UUID.class));
    }

    private void setPetRepositoryOffline() {
        given(petRepository.findByIdAndStatusIsNot(any(UUID.class), any(EntityStatus.class)))
                .willThrow(RuntimeException.class);
        given(petRepository.findByNameAndOwnerIdAndStatusIsNot(any(), any(UUID.class), any(EntityStatus.class), any(Pageable.class)))
                .willThrow(RuntimeException.class);
        given(petRepository.save(any(Pet.class)))
                .willThrow(RuntimeException.class);
        willThrow(RuntimeException.class)
                .given(petRepository)
                .deleteById(any(UUID.class));
    }

    @Then("the service will return {int} status code")
    public void theServiceWillReturnStatusCode(int statusCode) throws Exception {
        this.testData.getResultActions()
                .andExpect(status().is(statusCode));

        if (statusCode == HttpStatus.NO_CONTENT.value()) {
            Assert.assertTrue(StringUtils.isBlank(this.testData.getResultActions()
                    .andReturn()
                    .getResponse()
                    .getContentAsString()));
        }
    }

}
