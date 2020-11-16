package com.vfc.petz.domain.mappers;

import com.vfc.petz.domain.dto.PetRequest;
import com.vfc.petz.domain.dto.PetResponse;
import com.vfc.petz.domain.entity.Pet;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {DateMapper.class})
public interface PetMapper {

    PetResponse sourceToTarget(Pet pet, @Context TimeZone timeZone);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    Pet targetToSource(PetRequest petRequest, @Context TimeZone timeZone);

}
