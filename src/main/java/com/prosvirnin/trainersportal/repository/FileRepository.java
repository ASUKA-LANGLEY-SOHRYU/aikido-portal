package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    void deleteByName(String name);
}
