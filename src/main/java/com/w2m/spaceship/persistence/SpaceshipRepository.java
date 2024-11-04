package com.w2m.spaceship.persistence;

import com.w2m.spaceship.persistence.entity.SpaceshipDao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<SpaceshipDao, Long> {

  List<SpaceshipDao> findByNameContaining(String spaceshipName);

  /*@Query("select r from Rates r"
      + " where r.brandId = :brandId"
      + " and r.productId = :productId"
      + " and r.startDate <= :date"
      + " and r.endDate >= :date"
      + " order by r.priority desc")
  List<Rates> findByBrandIdAndProductIdAndDateOrderByPriority(@Param("brandId") Long brandId, @Param("productId") Long productId, @Param("date") LocalDateTime date);*/
}
