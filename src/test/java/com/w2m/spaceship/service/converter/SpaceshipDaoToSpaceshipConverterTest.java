package com.w2m.spaceship.service.converter;

import static org.junit.jupiter.api.Assertions.*;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.domain.Spaceship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.extensions.spring.test.ConverterScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SpaceshipDaoToSpaceshipConverterTest {
  @Configuration
  @ConverterScan(basePackageClasses = SpaceshipDaoToSpaceshipConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullSpaceshipDao_whenConvert_thenReturnNullSpaceship() {
    assertNull(conversionService.convert(null, Spaceship.class));
  }

  @Test
  void givenSpaceshipDao_whenConvert_thenReturnSpaceship() {
    SpaceshipDao spaceshipDao = SpaceshipDao.builder()
        .id(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();

    Spaceship spaceship = conversionService.convert(spaceshipDao, Spaceship.class);
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