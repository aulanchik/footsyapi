package io.aulanchik.footsyapi.repositories;

import io.aulanchik.footsyapi.entities.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findByName(String name);

    Page<List<Color>> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
