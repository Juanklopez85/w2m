package com.w2m.spaceship.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Error response")
public class ErrorDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "Error message", example = "Product not valid")
  private String message;

  @Schema(description = "Error code", example = "REQUIRED_FIELD")
  private ErrorCode errorCode;
}
