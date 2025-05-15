package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.group.ExclusionDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExclusionDateRepository extends JpaRepository<ExclusionDate, Long> {
    void deleteAllByGroup_Id(Long groupId);
}
