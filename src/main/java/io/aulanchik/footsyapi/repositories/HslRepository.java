package io.aulanchik.footsyapi.repositories;

import io.aulanchik.footsyapi.entities.Hsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HslRepository extends JpaRepository<Hsl, Long> {
}
