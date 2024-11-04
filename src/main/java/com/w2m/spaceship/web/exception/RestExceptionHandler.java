package com.w2m.spaceship.web.exception;

import com.w2m.spaceship.service.domain.NotFoundException;
import com.w2m.spaceship.web.model.ErrorCode;
import com.w2m.spaceship.web.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> requiredFieldException(Exception exception) {
    String message = "";
    if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
      FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
      if (fieldError != null) {
        String field = fieldError.getField();
        message = "'" + field + "' field is required";
      }
    } else {
      message = exception.getMessage();
    }
    ErrorDto errorDto = ErrorDto.builder()
        .message(message)
        .errorCode(ErrorCode.REQUIRED_FIELD)
        .build();
    return ResponseEntity.badRequest().body(errorDto);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
  @ResponseBody
  public ResponseEntity<ErrorDto> deserializationException(Exception exception) {
    ErrorDto errorDto = ErrorDto.builder()
        .message(exception.getMessage())
        .errorCode(ErrorCode.DESERIALIZATION_ERROR)
        .build();
    return ResponseEntity.badRequest().body(errorDto);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  public ResponseEntity<ErrorDto> notFoundException(Exception exception) {
    return createNotFoundErrorDto(ErrorCode.NOT_FOUND, exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public ResponseEntity<ErrorDto> illegalArgumentException(Exception exception) {
    return createNotFoundErrorDto(ErrorCode.ILLEGAL_ARGUMENT, exception.getMessage());
  }

  private ResponseEntity<ErrorDto> createNotFoundErrorDto(ErrorCode errorCode, String message) {
    ErrorDto errorDto = ErrorDto.builder()
        .message(message)
        .errorCode(errorCode)
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }
}
