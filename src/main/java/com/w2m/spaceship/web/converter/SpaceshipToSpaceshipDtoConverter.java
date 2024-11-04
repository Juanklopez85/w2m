package com.w2m.spaceship.web.converter;

import com.w2m.spaceship.service.domain.Spaceship;
import com.w2m.spaceship.web.model.SpaceshipDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface SpaceshipToSpaceshipDtoConverter extends Converter<Spaceship, SpaceshipDto> {
}
