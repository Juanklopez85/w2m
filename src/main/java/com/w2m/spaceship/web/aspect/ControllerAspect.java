package com.w2m.spaceship.web.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ControllerAspect {

  private final HttpServletRequest request;

  @Before("within(com.w2m..controller..*)")
  public void logRequest(JoinPoint joinPoint) {
    String path = request.getRequestURI();

    String method = request.getMethod();
    switch (method) {
      case "GET":
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)){
          log.info("{} - {}?{}", method, path, queryString);
        } else {
          log.info("{} - {}", method, path);
        }
        break;
      case "POST":
        Object[] signatureArgs = joinPoint.getArgs();
        log.info("{} - {} - Request:{}", method, path, getValueAsString(signatureArgs[0]));
        break;
      default:
        log.info("{} - {}", method, path);
    }
  }

  @AfterReturning(value = ("within(com.w2m..controller..*)"), returning = "returnValue")
  public void logResponse(JoinPoint joinPoint, Object returnValue) {
    ResponseEntity entityResponse = (ResponseEntity) returnValue;

    String path = request.getRequestURI();
    String method = request.getMethod();
    log.info("{} - {} - {} - Response:{}", method, path, entityResponse.getStatusCode().value(), getValueAsString(entityResponse.getBody()));
  }

  @AfterReturning(value = ("within(com.w2m..web.exception..*)"), returning = "returnValue")
  public void logErrorResponse(JoinPoint joinPoint, Object returnValue) {
    ResponseEntity entityResponse = (ResponseEntity) returnValue;

    String path = request.getRequestURI();
    String method = request.getMethod();
    log.error("{} - {} - {} - Response:{}", method, path, entityResponse.getStatusCode().value(), getValueAsString(entityResponse.getBody()));
  }

  private String getValueAsString(Object value) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    try {
      return mapper.writeValueAsString(value);
    } catch (JsonProcessingException ignored) {
      return null;
    }
  }
}
