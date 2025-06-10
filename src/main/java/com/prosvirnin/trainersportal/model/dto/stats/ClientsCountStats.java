package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * DTO с данными о росте количества учеников
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientsCountStats {
    /**
     * Процент роста количества учеников за выбранный период
     */
    private Double growthPercentage;

    /**
     * Общее количество учеников на текущий момент
     */
    private Integer totalClients;

    /**
     * Общее количество учеников за текущий месяц
     */
    private Integer clientsThisMonth;

    /**
     * Разница общего количества учеников по сравнению с предыдущим периодом
     * Положительное значение означает рост, отрицательное - снижение
     */
    private Integer clientsDifference;

    /**
     * Количество учеников на начало года
     */
    private Integer clientsAtStartOfYear;

    /**
     * Количество учеников на конец года (или на текущий момент, если год не завершен)
     */
    private Integer clientsAtEndOfYear;

    /**
     * Данные о количестве учеников по месяцам
     */
    private List<MonthlyClientCount> monthlyData;
}