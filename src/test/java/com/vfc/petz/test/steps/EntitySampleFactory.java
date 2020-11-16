package com.vfc.petz.test.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfc.petz.domain.dto.CustomerResponse;
import com.vfc.petz.domain.entity.Customer;
import com.vfc.petz.domain.exception.SystemException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.TimeZone;

@Component
public class EntitySampleFactory {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    public static String toJson(ObjectMapper objectMapper, Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SystemException("Error on convert the object to JSON");
        }
    }

    public static <T> T toObject(ObjectMapper objectMapper, String json, Class<T> classObject) {
        try {
            return objectMapper.readValue(json, classObject);
        } catch (JsonProcessingException e) {
            throw new SystemException("Error on convert from Json to object");
        }
    }

    public CustomerResponse createCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cpf(customer.getCpf())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .creationDate(toZonedDateTime(customer.getCreationDate()))
                .lastUpdate(toZonedDateTime(customer.getLastUpdate()))
                .build();
    }

    private ZonedDateTime toZonedDateTime(LocalDateTime dateTime) {
        return Optional.ofNullable(dateTime)
                .map(dt -> ZonedDateTime.of(dt, ZoneId.systemDefault()).withZoneSameInstant(SAO_PAULO_TIME_ZONE.toZoneId()))
                .orElse(null);
    }
}
