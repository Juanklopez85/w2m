package com.w2m.spaceship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.spaceship.persistence.SpaceshipRepository;
import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import com.w2m.spaceship.service.domain.NotFoundException;
import com.w2m.spaceship.service.domain.Spaceship;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SpaceshipServiceImplTest {

  @Mock
  private SpaceshipRepository spaceshipRepository;
  @Mock
  private ConversionService conversionService;
  @InjectMocks
  private SpaceshipServiceImpl spaceshipService;

  @Test
  void GivenPage_WhenGetSpaceshipsByPage_ThenCallDbWith10PageSize() {
    // Given
    ArgumentCaptor<Pageable> valueCapture = ArgumentCaptor.forClass(Pageable.class);
    when(spaceshipRepository.findAll(valueCapture.capture())).thenReturn(Page.empty());

    // When
    spaceshipService.getSpaceshipsByPage(0);

    // Then
    assertEquals(10, valueCapture.getValue().getPageSize());
    assertEquals(0, valueCapture.getValue().getPageNumber());
  }

  @Test
  void GivenPage_WhenGetSpaceshipsByPage_ThenConvertSpaceshipDaoToSpaceship() {
    // Given
    Page<SpaceshipDao> page = new PageImpl<>(Arrays.asList(
        SpaceshipDao.builder().build(),
        SpaceshipDao.builder().build(),
        SpaceshipDao.builder().build()));
    when(spaceshipRepository.findAll(any(Pageable.class))).thenReturn(page);

    when(conversionService.convert(any(SpaceshipDao.class), eq(Spaceship.class))).thenReturn(Spaceship.builder().build());

    // When
    List<Spaceship> spaceshipList = spaceshipService.getSpaceshipsByPage(0);

    // Then
    assertThat(spaceshipList).isNotEmpty().hasSize(3);
    verify(conversionService, times(3)).convert(any(SpaceshipDao.class), eq(Spaceship.class));
  }

  @Test
  void GivenSpaceshipId_WhenGetSpaceshipById_ThenRetrieveFromDB() {
    // Given
    SpaceshipDao spaceshipDao = SpaceshipDao.builder().build();
    when(spaceshipRepository.findById(10L)).thenReturn(Optional.of(spaceshipDao));

    Spaceship spaceship = Spaceship.builder().build();
    when(conversionService.convert(spaceshipDao, Spaceship.class)).thenReturn(spaceship);

    // When
    Spaceship result = spaceshipService.getSpaceshipById(10L);

    // Then
    assertEquals(result, spaceship);
  }

  @Test
  void GivenNoExistingSpaceshipId_WhenGetSpaceshipById_ThenThrowNotFoundException() {
    // Given
    when(spaceshipRepository.findById(10L)).thenReturn(Optional.empty());

    // When
    Exception exception = assertThrows(
        NotFoundException.class, () -> spaceshipService.getSpaceshipById(10L));

    assertEquals("Spaceship not found", exception.getMessage());
  }

  @Test
  void GivenSpaceshipName_WhenGetSpaceshipsByName_ThenVerifyIsOk() {
    // Given
    when(spaceshipRepository.findByNameContaining("test")).thenReturn(Arrays.asList(
        SpaceshipDao.builder().build(),
        SpaceshipDao.builder().build(),
        SpaceshipDao.builder().build()));

    when(conversionService.convert(any(SpaceshipDao.class), eq(Spaceship.class))).thenReturn(Spaceship.builder().build());

    // When
    List<Spaceship> spaceshipList = spaceshipService.getSpaceshipsByName("test");

    // Then
    assertThat(spaceshipList).isNotEmpty().hasSize(3);
    verify(conversionService, times(3)).convert(any(SpaceshipDao.class), eq(Spaceship.class));
  }

  @Test
  void GivenSpaceshipId_WhenDeleteSpaceship_ThenVerifyIsOk() {
    // Given
    doNothing().when(spaceshipRepository).deleteById(12L);

    // When
    spaceshipService.deleteSpaceship(12L);

    // Then
    verify(spaceshipRepository).deleteById(12L);
  }
}