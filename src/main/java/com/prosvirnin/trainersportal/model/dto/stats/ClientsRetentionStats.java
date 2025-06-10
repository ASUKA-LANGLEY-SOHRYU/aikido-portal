package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * DTO с данными об удержании учеников
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientsRetentionStats {
    /**
     * Общий процент удержания учеников за выбранный период
     */
    private Double overallRetentionRate;

    /**
     * Процент удержания учеников за год
     */
    private Double yearlyRetentionRate;

    /**
     * Разница среднего процента удержания учеников по сравнению с предыдущим периодом
     * Положительное значение означает улучшение, отрицательное - ухудшение
     */
    private Double retentionRateDifference;

    /**
     * Общее количество активных учеников
     */
    private Integer totalActiveClients;

    /**
     * Общее количество неактивных учеников (отсеявшихся)
     */
    private Integer totalInactiveClients;

    /**
     * Разница в количестве учеников (активных - неактивных)
     */
    private Integer clientsDifference;

    /**
     * Процент удержания по возрастным группам
     * Ключ - название возрастной группы, значение - процент удержания
     */
    private Map<String, Double> retentionByAgeGroup;

    /**
     * Данные о проценте удержания по месяцам
     */
    private List<MonthlyRetentionRate> monthlyData;
}