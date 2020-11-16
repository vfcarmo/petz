package com.vfc.petz.domain.mappers;

import com.vfc.petz.domain.dto.CustomerRequest;
import com.vfc.petz.domain.dto.CustomerResponse;
import com.vfc.petz.domain.dto.CustomerUpdateRequest;
import com.vfc.petz.domain.entity.Customer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface CustomerMapper {

    CustomerResponse sourceToTarget(Customer customer, @Context TimeZone timeZone);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    Customer targetToSource(CustomerRequest customerRequest, @Context TimeZone timeZone);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    void updateFromRequest(CustomerUpdateRequest customerUpdateRequest, @MappingTarget Customer customer);
}
