package com.vfc.petz.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "PageResponseBuilder", toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    private final int page;

    private final int pageSize;

    private final int total;

    private final List<T> items;

    @JsonCreator
    public static <T> PageResponse<T> create(
            @JsonProperty("page") int page,
            @JsonProperty("pageSize") int pageSize,
            @JsonProperty("total") int total,
            @JsonProperty("items") List<T> items) {

        return PageResponse.<T>builder()
                .page(page)
                .pageSize(pageSize)
                .total(total)
                .items(items)
                .build();
    }
}
