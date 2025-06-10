package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о количестве тренеров за конкретный месяц
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrainerCount {
    /**
     * Название месяца
     */
    private String month;
    
    /**
     * Год
     */
    private Integer year;
    
    /**
     * Количество тренеров
     */
    private Integer trainerCount;
    
    /**
     * Количество новых тренеров за месяц
     */
    private Integer newTrainersCount;
}