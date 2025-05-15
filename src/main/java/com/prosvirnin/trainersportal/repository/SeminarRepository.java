package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.seminar.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
}
