package com.w2m.spaceship.service;

import com.w2m.spaceship.persistence.SpaceshipRepository;
import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.aspect.annotation.LogTime;
import com.w2m.spaceship.service.domain.NotFoundException;
import com.w2m.spaceship.service.domain.Spaceship;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceshipServiceImpl implements SpaceshipService {

  private final SpaceshipRepository spaceshipRepository;
  private final ConversionService conversionService;

  @LogTime
  @Override
  public List<Spaceship> getSpaceshipsByPage(int page) {

    Pageable pageable = PageRequest.of(page, 10);
    return spaceshipRepository.findAll(pageable)
        .stream()
        .map(spaceship -> conversionService.convert(spaceship, Spaceship.class))
        .toList();
  }

  @Cacheable("spaceships")
  @LogTime
  @Override
  public Spaceship getSpaceshipById(long spaceshipId) {
    return spaceshipRepository.findById(spaceshipId)
        .map(spaceship -> conversionService.convert(spaceship, Spaceship.class))
        .orElseThrow(() -> new NotFoundException("Spaceship not found"));
  }

  @LogTime
  @Override
  public List<Spaceship> getSpaceshipsByName(final String spaceshipName) {
    return spaceshipRepository.findByNameContaining(spaceshipName)
        .stream()
        .map(spaceship -> conversionService.convert(spaceship, Spaceship.class))
        .toList();
  }

  @LogTime
  @Override
  public Spaceship modifySpaceship(final Spaceship spaceship) {
    if (getSpaceshipById(spaceship.getSpaceshipId()) == null) {
      throw new NotFoundException("Spaceship not found");
    }
    SpaceshipDao spaceshipDao = conversionService.convert(spaceship, SpaceshipDao.class);
    if (spaceshipDao == null) {
      throw new IllegalArgumentException("Spaceship cannot be null");
    }
    return conversionService.convert(spaceshipRepository.save(spaceshipDao), Spaceship.class);
  }

  @LogTime
  @Override
  public Spaceship createSpaceship(final Spaceship spaceship) {
    if (spaceship.getSpaceshipId() != null) {
      throw new IllegalArgumentException("Spaceship already exists");
    }
    SpaceshipDao spaceshipDao = conversionService.convert(spaceship, SpaceshipDao.class);
    if (spaceshipDao == null) {
      throw new IllegalArgumentException("Spaceship cannot be null");
    }
    return conversionService.convert(spaceshipRepository.save(spaceshipDao), Spaceship.class);
  }

  @LogTime
  @Override
  public void deleteSpaceship(final Long spaceshipId) {
    spaceshipRepository.deleteById(spaceshipId);
  }
}
