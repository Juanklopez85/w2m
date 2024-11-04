package com.w2m.spaceship.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SpaceshipRepositoryTest {

  @Autowired
  private SpaceshipRepository spaceshipRepository;

  @ParameterizedTest
  @MethodSource("generateData")
  void givenBrandIdAndProductIdAndDate_whenFindByBrandIdAndProductIdAndDateOrderByPriority_thenVerifyResult(LocalDateTime date, int size, List<Long> rateIdList) {
    /*List<Rates> ratesList = spaceshipRepository.findByBrandIdAndProductIdAndDateOrderByPriority(1L, 35455L, date);

    assertThat(ratesList).isNotEmpty().hasSize(size);

    for (int index = 0; index < size; index++) {
      assertThat(ratesList.get(index).getId()).isEqualTo(rateIdList.get(index));
    }*/
  }

  @Test
  void GivenSpaceshipName_WhenFindByNameContaining_ThenReturnsSpaceshipList() {
    // When
    List<SpaceshipDao> spaceshipDaoList = spaceshipRepository.findByNameContaining("wing");

    // Then
    assertThat(spaceshipDaoList).isNotEmpty().hasSize(5);

    for (SpaceshipDao spaceshipDao : spaceshipDaoList) {
      assertThat(spaceshipDao.getName()).contains("wing");
    }
  }

  static Stream<Arguments> generateData() {
    return Stream.of(
        Arguments.of(LocalDateTime.parse("2020-06-14T10:00:00"), 1, List.of(1L)),
        Arguments.of(LocalDateTime.parse("2020-06-14T16:00:00"), 2, List.of(2L, 1L)),
        Arguments.of(LocalDateTime.parse("2020-06-14T21:00:00"), 1, List.of(1L)),
        Arguments.of(LocalDateTime.parse("2020-06-15T10:00:00"), 2, List.of(3L, 1L)),
        Arguments.of(LocalDateTime.parse("2020-06-16T21:00:00"), 2, List.of(4L, 1L))
    );
  }
}