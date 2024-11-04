package com.w2m.spaceship.service.converter;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.domain.Spaceship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface SpaceshipToSpaceshipDaoConverter extends Converter<Spaceship,SpaceshipDao> {

  @Mapping(source = "spaceshipId", target = "id")
  SpaceshipDao convert(Spaceship spaceship);

}
