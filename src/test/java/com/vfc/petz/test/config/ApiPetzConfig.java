package com.vfc.petz.test.config;

import com.vfc.petz.domain.repository.CustomerRepository;
import com.vfc.petz.domain.repository.PetRepository;
import com.vfc.petz.test.ApiPetzTestData;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MockBean(CustomerRepository.class)
@MockBean(PetRepository.class)
public class ApiPetzConfig {

    @Bean
    public ApiPetzTestData apiPetzTestData() {
        return new ApiPetzTestData();
    }
}
