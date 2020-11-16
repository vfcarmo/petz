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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public PageResponse<CustomerResponse> listAll(String name, Pageable pageable) {

        final Page<Customer> customerPage = customerRepository.findByNameAndStatusIsNot(name, EntityStatus.EXCLUDED, pageable);

        return pageMapper.sourceToTarget(customerPage.map(customer -> customerMapper.sourceToTarget(customer, SAO_PAULO_TIME_ZONE)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerResponse findById(UUID customerId) {

        final Customer savedCustomer = findCustomerById(customerId);

        return customerMapper.sourceToTarget(savedCustomer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerResponse create(CustomerRequest customerRequest) {

        final Customer customer = customerMapper.targetToSource(customerRequest, SAO_PAULO_TIME_ZONE);
        customer.setStatus(EntityStatus.ACTIVE);

        final Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.sourceToTarget(savedCustomer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(UUID customerId, CustomerUpdateRequest customerUpdateRequest) {
        final Customer savedCustomer = findCustomerById(customerId);

        customerMapper.updateFromRequest(customerUpdateRequest, savedCustomer);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(UUID customerId) {

        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.EXCLUDED);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void activate(UUID customerId) {
        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.ACTIVE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivate(UUID customerId) {
        final Customer savedCustomer = findCustomerById(customerId);

        savedCustomer.setStatus(EntityStatus.INACTIVE);
    }

    private Customer findCustomerById(UUID customerId) {
        return customerRepository.findByIdAndStatusIsNot(customerId, EntityStatus.EXCLUDED)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

}
