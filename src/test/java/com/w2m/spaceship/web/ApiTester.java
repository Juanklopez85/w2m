package com.w2m.spaceship.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.test.web.servlet.ResultMatcher;

public class ApiTester {
  protected static String asJsonString(final Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected static ResultMatcher isDateBefore(String path) {
    LocalDateTime now = LocalDateTime.now();
    return mvcResult -> {
      String contentAsString = mvcResult.getResponse().getContentAsString();
      LocalDateTime checkoutDate = LocalDateTime.parse(JsonPath.read(contentAsString, path), DateTimeFormatter.ISO_DATE_TIME);
      if (!checkoutDate.isBefore(now)) {
        throw new AssertionError(String.format("Date: %s is not before %s", checkoutDate, now));
      }
    };
  }

  protected static ResultMatcher isDateAfter(String path) {
    LocalDateTime now = LocalDateTime.now();
    return mvcResult -> {
      String contentAsString = mvcResult.getResponse().getContentAsString();
      LocalDateTime checkoutDate = LocalDateTime.parse(JsonPath.read(contentAsString, path), DateTimeFormatter.ISO_DATE_TIME);
      if (!checkoutDate.isAfter(now)) {
        throw new AssertionError(String.format("Date: %s is not after %s", checkoutDate, now));
      }
    };
  }
}
