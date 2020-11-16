package com.vfc.petz.api;

import com.vfc.petz.domain.dto.CustomerRequest;
import com.vfc.petz.domain.dto.CustomerResponse;
import com.vfc.petz.domain.dto.CustomerUpdateRequest;
import com.vfc.petz.domain.dto.PageResponse;
import com.vfc.petz.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public PageResponse<CustomerResponse> listAll(@RequestParam(value = "name", required = false) String name,
                                                  @SortDefault(direction = Sort.Direction.DESC) Pageable pageable) {
        return customerService.listAll(name, pageable);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse findById(@PathVariable(name = "customerId") UUID customerId) {
        return customerService.findById(customerId);
    }

    @PostMapping
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.create(customerRequest);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable(name = "customerId") UUID customerId,
                               @RequestBody @Valid CustomerUpdateRequest customerUpdateRequest) {
        customerService.update(customerId, customerUpdateRequest);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(name = "customerId") UUID customerId) {
        customerService.deleteById(customerId);
    }

    @PatchMapping("/{customerId/activate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateCustomer(@PathVariable(name = "customerId") UUID customerId) {
        customerService.activate(customerId);
    }

    @PatchMapping("/{customerId/inactivate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateCustomer(@PathVariable(name = "customerId") UUID customerId) {
        customerService.inactivate(customerId);
    }
}
