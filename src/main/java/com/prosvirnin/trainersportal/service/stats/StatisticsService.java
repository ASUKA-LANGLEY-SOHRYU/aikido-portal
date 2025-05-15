package com.prosvirnin.trainersportal.service.stats;

import com.prosvirnin.trainersportal.model.dto.stats.*;

/**
 * Сервис для получения различных статистических показателей по ученикам, тренерам и семинарам.
 */
public interface StatisticsService {

    /**
     * Получает данные о посещаемости для указанного пользователя.
     *
     * @param id идентификатор пользователя
     * @return объект с данными посещаемости
     */
    AttendanceData getAttendanceDataByUserId(Long id);

    /**
     * Получает статистику роста количества учеников.
     *
     * @param filter параметры фильтрации (например, дата, клуб и т.д.)
     * @return объект с информацией о росте количества учеников
     */
    ClientsCountStats getGrowthRateOfStudentsCount(StatisticsFilterRequest filter);

    /**
     * Получает статистику удержания учеников.
     *
     * @param filter параметры фильтрации
     * @return объект с процентом удержания клиентов
     */
    ClientsRetentionStats getClientsRetentionStats(StatisticsFilterRequest filter);

    /**
     * Получает статистику роста количества тренеров.
     *
     * @param filter параметры фильтрации
     * @return объект с данными роста тренеров
     */
    TrainersGrowthRate getTrainersGrowthRate(StatisticsFilterRequest filter);

    /**
     * Получает процент активного участия учеников в семинарах.
     *
     * @param filter параметры фильтрации
     * @return процент активного участия
     */
    ActiveParticipationInSeminarsPercentage getActiveParticipationInSeminarsPercentage(StatisticsFilterRequest filter);

    /**
     * Получает процент учеников, прошедших аттестацию в семинарах.
     *
     * @param filter параметры фильтрации
     * @return процент сертифицированных участников
     */
    CertifiedParticipantsInSeminarsPercentage getCertifiedParticipantsInSeminarsPercentage(StatisticsFilterRequest filter);

    /**
     * Получает общую выручку от всех семинаров.
     *
     * @param filter параметры фильтрации
     * @return общая сумма доходов
     */
    SeminarsTotalIncome getSeminarsTotalIncome(StatisticsFilterRequest filter);
}
