package io.aulanchik.footsyapi.repositories;

import io.aulanchik.footsyapi.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
}
