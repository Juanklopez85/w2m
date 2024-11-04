package com.w2m.spaceship.web.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.w2m.spaceship.service.domain.Spaceship;
import com.w2m.spaceship.web.model.SpaceshipDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.extensions.spring.test.ConverterScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SpaceshipDtoToSpaceshipConverterTest {

  @Configuration
  @ConverterScan(basePackageClasses = SpaceshipDtoToSpaceshipConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullSpaceshipDto_whenConvert_thenReturnNullSpaceship() {
    assertNull(conversionService.convert(null, Spaceship.class));
  }

  @Test
  void givenSpaceshipDto_whenConvert_thenReturnSpaceship() {
    SpaceshipDto spaceshipDto = SpaceshipDto.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();

    Spaceship spaceship = conversionService.convert(spaceshipDto, Spaceship.class);
    assertNotNull(spaceship);
    assertAll(
        ()->assertEquals(1L, spaceship.getSpaceshipId()),
        ()->assertEquals("test name", spaceship.getName()),
        ()->assertEquals("test class", spaceship.getSpaceshipClass()),
        ()->assertEquals("test manufacturer", spaceship.getManufacturer()),
        ()->assertEquals("test model", spaceship.getModel()),
        ()->assertEquals(123L, spaceship.getCost()),
        ()->assertEquals(456.78f, spaceship.getLength()),
        ()->assertEquals(100L, spaceship.getPassengers())
    );
  }
}