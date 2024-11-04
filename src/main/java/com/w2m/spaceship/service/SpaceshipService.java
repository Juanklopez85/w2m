package com.w2m.spaceship.service;

import com.w2m.spaceship.service.domain.Spaceship;
import java.util.List;

public interface SpaceshipService {

  List<Spaceship> getSpaceshipsByPage(int page);

  Spaceship getSpaceshipById(long spaceshipId);

  List<Spaceship> getSpaceshipsByName(String spaceshipName);

  Spaceship modifySpaceship(Spaceship spaceship);

  Spaceship createSpaceship(Spaceship spaceship);

  void deleteSpaceship(Long spaceshipId);
}