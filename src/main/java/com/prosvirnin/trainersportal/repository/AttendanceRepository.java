package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.group.Attendance;
import com.prosvirnin.trainersportal.model.dto.group.AttendanceDto;
import com.prosvirnin.trainersportal.model.dto.group.JournalForClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

    @Query("""
    SELECT new com.prosvirnin.trainersportal.model.dto.group.JournalForClientDto(
        CAST(:year as int ) ,
        cast(:month as int ),
        g.id,
        g.name,
        g.ageGroup,
        c.id,
        c.name,
        c.address,
        (
            SELECT COUNT(s)
            FROM Schedule s
            WHERE s.group.id = g.id
              AND FUNCTION('MONTH', s.dayOfWeek) BETWEEN 1 AND 7
              AND NOT EXISTS (
                  SELECT ed FROM ExclusionDate ed
                  WHERE ed.group.id = g.id
                    AND FUNCTION('MONTH', ed.date) = :month
                    AND FUNCTION('YEAR', ed.date) = :year
              )
        ),
        (
            SELECT COUNT(a)
            FROM Attendance a
            WHERE a.group.id = g.id
              AND a.user.id = :userId
              AND FUNCTION('MONTH', a.date) = :month
              AND FUNCTION('YEAR', a.date) = :year
        )
    )
    FROM Group g
    JOIN g.club c
    JOIN g.users u
    WHERE u.id = :userId
""")
    Collection<JournalForClientDto> findJournalForClientSummary( //TODO: Найти причину или переписать на Object[]
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
    SELECT
        FUNCTION('YEAR', a.date),
        FUNCTION('MONTH', a.date),
        u.id,
        u.fullName,
        u.kyu,
        g.id,
        g.name,
        g.ageGroup,
        c.id,
        c.name,
        c.address,
        (
            SELECT COUNT(a2)
            FROM Attendance a2
            WHERE a2.group.id = g.id
              AND a2.user.id = u.id
              AND a2.date BETWEEN :startDate AND :endDate
        )
    FROM Attendance a
    JOIN a.group g
    JOIN g.club c
    JOIN a.user u
    WHERE a.date BETWEEN :startDate AND :endDate
      AND (:groupId IS NULL OR g.id = :groupId)
    GROUP BY u.id, u.fullName, u.kyu,
             g.id, g.name, g.ageGroup,
             c.id, c.name, c.address,
             FUNCTION('YEAR', a.date), FUNCTION('MONTH', a.date)
""")
    List<Object[]> findTrainerJournalRaw(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("groupId") Long groupId
    );

    @Query("""
    SELECT new com.prosvirnin.trainersportal.model.dto.group.AttendanceDto(
        a.id,
        g.id,
        a.user.id,
        a.date
    )
    FROM Attendance a
    JOIN a.group g
    WHERE a.user.id = :userId
      AND FUNCTION('YEAR', a.date) = :dateYear
      AND FUNCTION('MONTH', a.date) = :dateMonth
""")
    Collection<AttendanceDto> findAttendanceDtoByUserIdAndYearAndMonth(Long userId, int dateYear, int dateMonth);
}
