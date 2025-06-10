package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO с данными о росте количества тренеров (мастеров)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainersGrowthRate {
    /**
     * Процент роста количества тренеров за выбранный период
     */
    private Double growthPercentage;

    /**
     * Общее количество тренеров на текущий момент
     */
    private Integer totalTrainers;

    /**
     * Общее количество тренеров за текущий месяц
     */
    private Integer trainersThisMonth;

    /**
     * Разница общего количества тренеров по сравнению с предыдущим периодом
     * Положительное значение означает рост, отрицательное - снижение
     */
    private Integer trainersDifference;

    /**
     * Количество тренеров на начало года
     */
    private Integer trainersAtStartOfYear;

    /**
     * Количество тренеров на конец года (или на текущий момент, если год не завершен)
     */
    private Integer trainersAtEndOfYear;

    /**
     * Данные о количестве тренеров по месяцам
     */
    private List<MonthlyTrainerCount> monthlyData;
}