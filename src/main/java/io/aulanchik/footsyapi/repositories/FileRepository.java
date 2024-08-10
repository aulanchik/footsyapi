package io.aulanchik.footsyapi.repositories;

import io.aulanchik.footsyapi.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByName(String filename);
}
