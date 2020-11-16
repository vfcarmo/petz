package com.vfc.petz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApiPetzTestData {

    private String requestJson;
    private ResultActions resultActions;
    private LocalDateTime now;

    public ApiPetzTestData() {
        this.reset();
    }

    public void reset() {
        this.requestJson = null;
        this.resultActions = null;
        this.now = null;
    }
}
