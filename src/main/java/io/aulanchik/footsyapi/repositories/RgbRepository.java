package io.aulanchik.footsyapi.repositories;

import io.aulanchik.footsyapi.entities.Rgb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RgbRepository extends JpaRepository<Rgb, Long> {
}
