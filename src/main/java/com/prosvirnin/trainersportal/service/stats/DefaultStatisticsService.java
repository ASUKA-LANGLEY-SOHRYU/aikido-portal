package com.prosvirnin.trainersportal.service.stats;

import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.group.AttendanceDto;
import com.prosvirnin.trainersportal.model.dto.stats.*;
import com.prosvirnin.trainersportal.repository.AttendanceRepository;
import com.prosvirnin.trainersportal.repository.ScheduleRepository;
import com.prosvirnin.trainersportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultStatisticsService implements StatisticsService{

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public AttendanceData getAttendanceDataByUserId(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFound("Пользователь с id " + id + " не найден"));


        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        YearMonth previousMonth = currentMonth.minusMonths(1);


        Collection<AttendanceDto> currentMonthAttendances =
                attendanceRepository.findAttendanceDtoByUserIdAndYearAndMonth(
                        id, currentMonth.getYear(), currentMonth.getMonthValue());

        Collection<com.prosvirnin.trainersportal.model.dto.group.AttendanceDto> previousMonthAttendances =
                attendanceRepository.findAttendanceDtoByUserIdAndYearAndMonth(
                        id, previousMonth.getYear(), previousMonth.getMonthValue());


        Map<String, Integer> monthlyAttendances = new LinkedHashMap<>();
        for (int i = 11; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            Collection<com.prosvirnin.trainersportal.model.dto.group.AttendanceDto> monthAttendances =
                    attendanceRepository.findAttendanceDtoByUserIdAndYearAndMonth(
                            id, month.getYear(), month.getMonthValue());

            String monthKey = month.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")) + " " + month.getYear();
            monthlyAttendances.put(monthKey, monthAttendances.size());
        }


        Collection<com.prosvirnin.trainersportal.model.dto.group.JournalForClientDto> journalData =
                attendanceRepository.findJournalForClientSummary(
                        id, currentMonth.getYear(), currentMonth.getMonthValue());


        int totalPossibleAttendances = 20;


        int totalAttendances = currentMonthAttendances.size();


        double averageAttendancePercentage = totalPossibleAttendances > 0
                ? (double) totalAttendances / totalPossibleAttendances * 100
                : 0;


        int previousMonthAttendancesCount = previousMonthAttendances.size();
        int attendanceDifference = totalAttendances - previousMonthAttendancesCount;


        double attendanceChangePercentage = previousMonthAttendancesCount > 0
                ? (double) attendanceDifference / previousMonthAttendancesCount * 100
                : 0;


        return AttendanceData.builder()
                .userId(id)
                .averageAttendancePercentage(Math.round(averageAttendancePercentage * 10) / 10.0)
                .totalAttendances(totalAttendances)
                .totalPossibleAttendances(totalPossibleAttendances)
                .monthlyAttendances(monthlyAttendances)
                .attendanceDifference(attendanceDifference)
                .attendanceChangePercentage(Math.round(attendanceChangePercentage * 10) / 10.0)
                .build();
    }

    @Override
    public ClientsCountStats getGrowthRateOfStudentsCount(StatisticsFilterRequest filter) {

        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);


        LocalDate startDate = filter != null && filter.getStartDate() != null
                ? filter.getStartDate()
                : startOfYear;

        LocalDate endDate = filter != null && filter.getEndDate() != null
                ? filter.getEndDate()
                : today;


        Integer clientsAtStart = userRepository.countClientsByRegistrationDateBefore(startDate);
        Integer clientsAtEnd = userRepository.countClientsByRegistrationDateBefore(endDate.plusDays(1));


        Integer clientsAtStartOfYear = userRepository.countClientsByRegistrationDateBefore(startOfYear);
        Integer clientsAtEndOfYear = userRepository.countClientsByRegistrationDateBefore(today.plusDays(1));


        YearMonth currentMonth = YearMonth.from(today);
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();
        Integer clientsThisMonth = userRepository.countClientsByRegistrationDateBetween(startOfMonth, endOfMonth);


        YearMonth previousMonth = currentMonth.minusMonths(1);
        LocalDate startOfPreviousMonth = previousMonth.atDay(1);
        LocalDate endOfPreviousMonth = previousMonth.atEndOfMonth();
        Integer clientsPreviousMonth = userRepository.countClientsByRegistrationDateBetween(startOfPreviousMonth, endOfPreviousMonth);


        Integer clientsDifference = clientsThisMonth - clientsPreviousMonth;


        Double growthPercentage = clientsAtStart > 0
                ? ((double) (clientsAtEnd - clientsAtStart) / clientsAtStart) * 100
                : 0.0;


        List<MonthlyClientCount> monthlyData = new ArrayList<>();
        YearMonth month = YearMonth.from(today);

        for (int i = 11; i >= 0; i--) {
            YearMonth currentMonthData = month.minusMonths(i);
            LocalDate monthStart = currentMonthData.atDay(1);
            LocalDate monthEnd = currentMonthData.atEndOfMonth();


            Integer clientsCount = userRepository.countClientsByRegistrationDateBefore(monthEnd.plusDays(1));


            Integer newClientsCount = userRepository.countClientsByRegistrationDateBetween(monthStart, monthEnd);

            monthlyData.add(MonthlyClientCount.builder()
                    .month(currentMonthData.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")))
                    .year(currentMonthData.getYear())
                    .clientCount(clientsCount)
                    .newClientsCount(newClientsCount)
                    .build());
        }


        return ClientsCountStats.builder()
                .growthPercentage(Math.round(growthPercentage * 10) / 10.0)
                .totalClients(clientsAtEnd)
                .clientsThisMonth(clientsThisMonth)
                .clientsDifference(clientsDifference)
                .clientsAtStartOfYear(clientsAtStartOfYear)
                .clientsAtEndOfYear(clientsAtEndOfYear)
                .monthlyData(monthlyData)
                .build();
    }

    @Override
    public ClientsRetentionStats getClientsRetentionStats(StatisticsFilterRequest filter) {

        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);


        LocalDate startDate = filter != null && filter.getStartDate() != null
                ? filter.getStartDate()
                : startOfYear;

        LocalDate endDate = filter != null && filter.getEndDate() != null
                ? filter.getEndDate()
                : today;



        LocalDate activeThreshold = today.minusDays(30);

        Integer totalActiveClients = attendanceRepository.countDistinctUsersByAttendanceDateAfter(activeThreshold);
        Integer totalClients = userRepository.countByRoles(Role.CLIENT);
        Integer totalInactiveClients = totalClients - totalActiveClients;


        Integer clientsDifference = totalActiveClients - totalInactiveClients;


        Double overallRetentionRate = totalClients > 0
                ? ((double) totalActiveClients / totalClients) * 100
                : 0.0;


        LocalDate previousPeriodStart = startDate.minusDays(endDate.toEpochDay() - startDate.toEpochDay());
        LocalDate previousPeriodEnd = startDate.minusDays(1);

        // Получаем количество активных учеников за предыдущий период
        Integer previousActiveClients = attendanceRepository.countDistinctUsersByAttendanceDateBetween(
                previousPeriodStart, previousPeriodEnd);
        Integer previousTotalClients = userRepository.countClientsByRegistrationDateBefore(previousPeriodEnd.plusDays(1));

        // Рассчитываем процент удержания за предыдущий период
        Double previousRetentionRate = previousTotalClients > 0
                ? ((double) previousActiveClients / previousTotalClients) * 100
                : 0.0;

        // Рассчитываем разницу в проценте удержания
        Double retentionRateDifference = overallRetentionRate - previousRetentionRate;

        // Рассчитываем процент удержания за год
        Integer yearlyActiveClients = attendanceRepository.countDistinctUsersByAttendanceDateBetween(
                startOfYear, today);
        Integer yearlyTotalClients = userRepository.countClientsByRegistrationDateBefore(today.plusDays(1));

        Double yearlyRetentionRate = yearlyTotalClients > 0
                ? ((double) yearlyActiveClients / yearlyTotalClients) * 100
                : 0.0;

        // Получаем данные по возрастным группам
        Map<String, Double> retentionByAgeGroup = new HashMap<>();

        // Для каждой возрастной группы рассчитываем процент удержания
        for (AgeGroup ageGroup : AgeGroup.values()) {
            Integer activeClientsInGroup = attendanceRepository.countDistinctUsersByAttendanceDateAfterAndAgeGroup(
                    activeThreshold, ageGroup);
            Integer totalClientsInGroup = userRepository.countByRolesAndAgeGroup(Role.CLIENT, ageGroup);

            Double retentionRate = totalClientsInGroup > 0
                    ? ((double) activeClientsInGroup / totalClientsInGroup) * 100
                    : 0.0;

            retentionByAgeGroup.put(ageGroup.getDisplayName(), Math.round(retentionRate * 10) / 10.0);
        }

        // Получаем данные по месяцам за последний год
        List<MonthlyRetentionRate> monthlyData = new ArrayList<>();
        YearMonth month = YearMonth.from(today);

        for (int i = 11; i >= 0; i--) {
            YearMonth currentMonthData = month.minusMonths(i);
            LocalDate monthStart = currentMonthData.atDay(1);
            LocalDate monthEnd = currentMonthData.atEndOfMonth();

            // Получаем количество активных учеников за месяц
            Integer activeClientsInMonth = attendanceRepository.countDistinctUsersByAttendanceDateBetween(
                    monthStart, monthEnd);

            // Получаем общее количество учеников на конец месяца
            Integer totalClientsInMonth = userRepository.countClientsByRegistrationDateBefore(monthEnd.plusDays(1));

            // Рассчитываем количество неактивных учеников
            Integer inactiveClientsInMonth = totalClientsInMonth - activeClientsInMonth;

            // Рассчитываем процент удержания
            double retentionRate = totalClientsInMonth > 0
                    ? ((double) activeClientsInMonth / totalClientsInMonth) * 100
                    : 0.0;

            monthlyData.add(MonthlyRetentionRate.builder()
                    .month(currentMonthData.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")))
                    .year(currentMonthData.getYear())
                    .retentionRate(Math.round(retentionRate * 10) / 10.0)
                    .activeClients(activeClientsInMonth)
                    .inactiveClients(inactiveClientsInMonth)
                    .build());
        }

        // Формируем и возвращаем результат
        return ClientsRetentionStats.builder()
                .overallRetentionRate(Math.round(overallRetentionRate * 10) / 10.0)
                .yearlyRetentionRate(Math.round(yearlyRetentionRate * 10) / 10.0)
                .retentionRateDifference(Math.round(retentionRateDifference * 10) / 10.0)
                .totalActiveClients(totalActiveClients)
                .totalInactiveClients(totalInactiveClients)
                .clientsDifference(clientsDifference)
                .retentionByAgeGroup(retentionByAgeGroup)
                .monthlyData(monthlyData)
                .build();
    }

    @Override
    public TrainersGrowthRate getTrainersGrowthRate(StatisticsFilterRequest filter) {
        return null;
    }

    @Override
    public ActiveParticipationInSeminarsPercentage getActiveParticipationInSeminarsPercentage(StatisticsFilterRequest filter) {
        return null;
    }

    @Override
    public CertifiedParticipantsInSeminarsPercentage getCertifiedParticipantsInSeminarsPercentage(StatisticsFilterRequest filter) {
        return null;
    }

    @Override
    public SeminarsTotalIncome getSeminarsTotalIncome(StatisticsFilterRequest filter) {
        return null;
    }
}
