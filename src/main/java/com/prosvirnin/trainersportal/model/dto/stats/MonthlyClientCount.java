package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные о количестве учеников за конкретный месяц
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyClientCount {
    /**
     * Название месяца
     */
    private String month;
    
    /**
     * Год
     */
    private Integer year;
    
    /**
     * Количество учеников
     */
    private Integer clientCount;
    
    /**
     * Количество новых учеников за месяц
     */
    private Integer newClientsCount;
}