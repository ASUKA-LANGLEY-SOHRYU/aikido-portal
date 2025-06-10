package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о проценте удержания учеников за конкретный месяц
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRetentionRate {
    /**
     * Название месяца
     */
    private String month;
    
    /**
     * Год
     */
    private Integer year;
    
    /**
     * Процент удержания учеников
     */
    private Double retentionRate;
    
    /**
     * Количество активных учеников
     */
    private Integer activeClients;
    
    /**
     * Количество неактивных учеников
     */
    private Integer inactiveClients;
}