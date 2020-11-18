package com.vfc.petz.domain.service;

import com.vfc.petz.domain.dto.CustomerRequest;
import com.vfc.petz.domain.dto.CustomerResponse;
import com.vfc.petz.domain.dto.CustomerUpdateRequest;
import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.exception.CustomerNotFoundException;
import com.vfc.petz.domain.mappers.CustomerMapper;
import com.vfc.petz.domain.mappers.PageMapper;
import com.vfc.petz.domain.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class CustomerService {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PageMapper pageMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, PageMapper pageMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.pageMapper = pageMapper;
    }

    @Transactional
    public PageResponse<CustomerResponse> listAll(String name, Pageable pageable) {

        String queryParameter = Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .map(parameter -> parameter.toUpperCase() + "%")
                .orElse(null);

        final Page<Customer> customerPage = customerRepository.findByNameAndStatusIsNot(queryParameter, EntityStatus.EXCLUDED, pageable);

        return pageMapper.sourceToTarget(customerPage.map(customer -> customerMapper.sourceToTarget(customer, SAO_PAULO_TIME_ZONE)));
    }

    @Transactional
    public CustomerResponse findById(UUID customerId) {

        final Customer savedCustomer = findCustomerById(customerId);

        return customerMapper.sourceToTarget(savedCustomer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional
    public CustomerResponse create(CustomerRequest customerRequest) {

        final Customer customer = customerMapper.targetToSource(customerRequest, SAO_PAULO_TIME_ZONE);
        customer.setStatus(EntityStatus.ACTIVE);

        final Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.sourceToTarget(savedCustomer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional
    public void update(UUID customerId, CustomerUpdateRequest customerUpdateRequest) {
        final Customer savedCustomer = findCustomerById(customerId);

        customerMapper.updateFromRequest(customerUpdateRequest, savedCustomer);
    }

    @Transactional
    public void deleteById(UUID customerId) {

        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.EXCLUDED);
    }

    @Transactional
    public void activate(UUID customerId) {
        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.ACTIVE);
    }

    @Transactional
    public void inactivate(UUID customerId) {
        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.INACTIVE);
    }

    private Customer findCustomerById(UUID customerId) {
        return customerRepository.findByIdAndStatusIsNot(customerId, EntityStatus.EXCLUDED)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

}
