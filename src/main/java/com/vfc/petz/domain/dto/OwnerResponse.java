package com.vfc.petz.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.vfc.petz.domain.entity.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(builderClassName = "OwnerResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OwnerResponse.OwnerResponseBuilder.class)
public class OwnerResponse {

    private final UUID id;

    private final String name;

    private final String phone;

    private final String email;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OwnerResponseBuilder {

    }
}
