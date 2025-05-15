package com.prosvirnin.trainersportal.service.stats;

import com.prosvirnin.trainersportal.model.dto.stats.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultStatisticsService implements StatisticsService{
    @Override
    public AttendanceData getAttendanceDataByUserId(Long id) {
        return null;
    }

    @Override
    public ClientsCountStats getGrowthRateOfStudentsCount(StatisticsFilterRequest filter) {
        return null;
    }

    @Override
    public ClientsRetentionStats getClientsRetentionStats(StatisticsFilterRequest filter) {
        return null;
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
