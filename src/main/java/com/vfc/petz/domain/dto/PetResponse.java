package com.vfc.petz.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.vfc.petz.domain.entity.EntityStatus;
import com.vfc.petz.domain.entity.PetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(builderClassName = "PetResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = PetResponse.PetResponseBuilder.class)
public class PetResponse {

    private final UUID id;

    private final String name;

    private final PetType type;

    private final String observation;

    private final LocalDateTime birthdate;

    private final OwnerResponse owner;

    private final EntityStatus status;

    private final ZonedDateTime creationDate;

    private final ZonedDateTime lastUpdate;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PetResponseBuilder {

    }
}
