package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.technique.Technique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Long> {
}
