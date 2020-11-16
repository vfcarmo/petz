package com.vfc.petz.domain.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public class DateMapper {

    public ZonedDateTime fromDate(LocalDateTime dateTime, @Context TimeZone timeZone) {
        return Optional.ofNullable(dateTime)
                .map(dt -> ZonedDateTime.of(dt, ZoneId.systemDefault()).withZoneSameInstant(timeZone.toZoneId()))
                .orElse(null);
    }
}
