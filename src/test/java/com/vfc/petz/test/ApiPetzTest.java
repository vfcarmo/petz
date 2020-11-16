package com.vfc.petz.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = "com.vfc.petz.test",
        plugin = {"pretty", "html:build/cucumber-html-report"},
//        tags = {},
        strict = true)
public class ApiPetzTest {
}
