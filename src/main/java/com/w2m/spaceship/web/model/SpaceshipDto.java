package com.w2m.spaceship.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Spaceship response")
public class SpaceshipDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "Id of the spaceship", example = "1")
  private Long spaceshipId;
  @Schema(description = "Name of the spaceship", example = "Random spaceship")
  private String name;
  @Schema(description = "Spaceship class", example = "Random class")
  private String spaceshipClass;
  @Schema(description = "Name of the spaceship", example = "Random manufacturer")
  private String manufacturer;
  @Schema(description = "Model of the spaceship", example = "Random model")
  private String model;
  @Schema(description = "Total cost", example = "100")
  private Long cost;
  @Schema(description = "Length of the spaceship", example = "150.5")
  private float length;
  @Schema(description = "Total passengers", example = "200")
  private Long passengers;
}
