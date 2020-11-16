package com.vfc.petz.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CustomerRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = CustomerRequest.CustomerRequestBuilder.class)
public class CustomerRequest {

    @NotBlank
    private final String name;

    @NotNull
    @Size(min = 3, max = 11)
    private final String cpf;

    @NotNull
    @Size(min = 10, max = 11)
    private final String phone;

    @Email
    private final String email;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerRequestBuilder {

    }
}
