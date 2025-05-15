package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.domain.group.AgeGroup;
import com.prosvirnin.trainersportal.model.domain.group.Attendance;
import com.prosvirnin.trainersportal.model.domain.group.Group;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.group.*;
import com.prosvirnin.trainersportal.repository.AttendanceRepository;
import com.prosvirnin.trainersportal.repository.ScheduleRepository;
import com.prosvirnin.trainersportal.service.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultAttendanceService implements AttendanceService{
    private final AttendanceRepository attendanceRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AttendanceDto markAttendance(AttendanceMarkRequest request) {
        Group group = entityManager.getReference(Group.class, request.getGroupId());
        User user = entityManager.getReference(User.class, request.getUserId());

        Attendance attendance = Attendance.builder()
                .date(request.getDate())
                .group(group)
                .user(user)
                .build();
        attendance = attendanceRepository.save(attendance);

        return AttendanceDto.builder()
                .id(attendance.getId())
                .date(attendance.getDate())
                .groupId(group.getId())
                .userId(user.getId())
                .build();
    }

    @Override
    public Collection<JournalForClientDto> getJournalForClient(UserDetails clientDetails, JournalRequest request) {
        User user = userService.findByLogin(clientDetails.getUsername());
        int year = request.getYearAndMonthFrom().getYear();
        int month = request.getYearAndMonthFrom().getMonthValue();
        Collection<JournalForClientDto> journalForClientSummary =
                attendanceRepository.findJournalForClientSummary(user.getId(), year, month);
        journalForClientSummary.forEach(item -> {
            item.setSchedule(scheduleRepository.findAllDtoByGroupId(item.getGroupId()));
            item.setAttendance(
                    attendanceRepository.findAttendanceDtoByUserIdAndYearAndMonth(
                            user.getId(),
                            year,
                            month
                    )
            );
        });
        return journalForClientSummary;
    }

    @Override
    public Collection<JournalForTrainerDto> getJournalForTrainer(JournalRequest request) {
        int yearFrom = request.getYearAndMonthFrom().getYear();
        int monthFrom = request.getYearAndMonthFrom().getMonthValue();
        int yearTo = request.getYearAndMonthTo().getYear();
        int monthTo = request.getYearAndMonthTo().getMonthValue();
        Collection<JournalForTrainerDto> journalForTrainerSummary =
                getTrainerJournal(
                        LocalDate.of(yearFrom, monthFrom, 1),
                        LocalDate.of(yearTo, monthTo, 1),
                        request.getGroupId()
                );
        journalForTrainerSummary.forEach( item -> {
            item.setSchedule(scheduleRepository.findAllDtoByGroupId(item.getGroupId()));
            item.setAttendance(
                    attendanceRepository.findAttendanceDtoByUserIdAndYearAndMonth(
                        item.getClientId(),
                        item.getYear(),
                        item.getMonth()
                    )
            );
        });

        return journalForTrainerSummary;
    }

    private List<JournalForTrainerDto> getTrainerJournal(LocalDate startDate, LocalDate endDate, Long groupId) {
        List<Object[]> raw = attendanceRepository.findTrainerJournalRaw(startDate, endDate, groupId);

        return raw.stream().map(row -> new JournalForTrainerDto(
                (Integer) row[0],
                (Integer) row[1],
                (Long) row[2],
                (String) row[3],
                (Integer) row[4],
                (Long) row[5],
                (String) row[6],
                (AgeGroup) row[7],
                (Long) row[8],
                (String) row[9],
                (String) row[10],
                (Long) row[11]
        )).toList();
    }
}
