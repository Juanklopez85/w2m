package com.w2m.spaceship.service.domain;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Spaceship {
  private Long spaceshipId;

  private String name;
  private String spaceshipClass;
  private String manufacturer;
  private String model;
  private Long cost;
  private float length;
  private Long passengers;
}
