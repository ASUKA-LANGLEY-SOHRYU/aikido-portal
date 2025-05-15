package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.group.Schedule;
import com.prosvirnin.trainersportal.model.dto.group.ScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.ScheduleDto(
            s.group.id, s.dayOfWeek, s.startTime, s.endTime)
        FROM Schedule s
        WHERE s.group.id = :groupId
""")
    List<ScheduleDto> findAllDtoByGroupId(Long groupId);

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.ScheduleDto(
            s.group.id, s.dayOfWeek, s.startTime, s.endTime)
        FROM Schedule s
        WHERE s.group.club.id = :clubId
""")
    List<ScheduleDto> findAllDtoByClubId(Long clubId);

    void deleteAllByGroup_Id(Long groupId);

    Collection<Schedule> findAllByGroup_Id(Long groupId);
}
