package com.w2m.spaceship.web.converter;

import static org.junit.jupiter.api.Assertions.*;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.converter.SpaceshipToSpaceshipDaoConverter;
import com.w2m.spaceship.service.domain.Spaceship;
import com.w2m.spaceship.web.model.SpaceshipDto;
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
class SpaceshipToSpaceshipDtoConverterTest {

  @Configuration
  @ConverterScan(basePackageClasses = SpaceshipToSpaceshipDtoConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullSpaceship_whenConvert_thenReturnNullSpaceshipDto() {
    assertNull(conversionService.convert(null, SpaceshipDto.class));
  }

  @Test
  void givenSpaceship_whenConvert_thenReturnSpaceshipDto() {
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

    SpaceshipDto spaceshipDto = conversionService.convert(spaceship, SpaceshipDto.class);
    assertNotNull(spaceshipDto);
    assertAll(
        ()->assertEquals(1L, spaceshipDto.getSpaceshipId()),
        ()->assertEquals("test name", spaceshipDto.getName()),
        ()->assertEquals("test class", spaceshipDto.getSpaceshipClass()),
        ()->assertEquals("test manufacturer", spaceshipDto.getManufacturer()),
        ()->assertEquals("test model", spaceshipDto.getModel()),
        ()->assertEquals(123L, spaceshipDto.getCost()),
        ()->assertEquals(456.78f, spaceshipDto.getLength()),
        ()->assertEquals(100L, spaceshipDto.getPassengers())
    );
  }
}