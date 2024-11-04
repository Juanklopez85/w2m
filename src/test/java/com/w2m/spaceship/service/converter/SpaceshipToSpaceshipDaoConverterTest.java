package com.w2m.spaceship.service.converter;

import static org.junit.jupiter.api.Assertions.*;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.domain.Spaceship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.extensions.spring.test.ConverterScan;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SpaceshipToSpaceshipDaoConverterTest {
  @Configuration
  @ConverterScan(basePackageClasses = SpaceshipToSpaceshipDaoConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullSpaceship_whenConvert_thenReturnNullSpaceshipDao() {
    assertNull(conversionService.convert(null, SpaceshipDao.class));
  }

  @Test
  void givenSpaceship_whenConvert_thenReturnSpaceshipDao() {
    Spaceship spaceship = Spaceship.builder()
        .spaceshipId(1L)
        .name("test name")
        .spaceshipClass("test class")
        .manufacturer("test manufacturer")
        .model("test model")
        .cost(123L)
        .length(456.78f)
        .passengers(100L)
        .build();

    SpaceshipDao spaceshipDao = conversionService.convert(spaceship, SpaceshipDao.class);
    assertNotNull(spaceshipDao);
    assertAll(
        ()->assertEquals(1L, spaceshipDao.getId()),
        ()->assertEquals("test name", spaceshipDao.getName()),
        ()->assertEquals("test class", spaceshipDao.getSpaceshipClass()),
        ()->assertEquals("test manufacturer", spaceshipDao.getManufacturer()),
        ()->assertEquals("test model", spaceshipDao.getModel()),
        ()->assertEquals(123L, spaceshipDao.getCost()),
        ()->assertEquals(456.78f, spaceshipDao.getLength()),
        ()->assertEquals(100L, spaceshipDao.getPassengers())
    );
  }
}