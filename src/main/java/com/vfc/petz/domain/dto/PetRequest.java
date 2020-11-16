package com.vfc.petz.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.vfc.petz.domain.entity.PetType;
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
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "PetRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = PetRequest.PetRequestBuilder.class)
public class PetRequest {

    @NotBlank
    private final String name;

    @NotNull
    private final PetType type;

    private final String observation;

    @NotNull
    private final LocalDateTime birthdate;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PetRequestBuilder {

    }
}
