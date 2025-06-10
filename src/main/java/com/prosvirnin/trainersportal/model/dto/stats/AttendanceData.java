package com.prosvirnin.trainersportal.model.dto.stats;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AttendanceData {
    /**
     * Идентификатор пользователя
     */
    private Long userId;

    /**
     * Средний процент посещаемости ученика
     */
    private Double averageAttendancePercentage;

    /**
     * Общее количество посещений за выбранный период
     */
    private Integer totalAttendances;

    /**
     * Общее количество возможных посещений за выбранный период
     */
    private Integer totalPossibleAttendances;

    /**
     * Общее количество посещений за каждый месяц
     * Ключ - название месяца (например, "Январь 2023")
     * Значение - количество посещений
     */
    private Map<String, Integer> monthlyAttendances;

    /**
     * Разница общего количества посещений по сравнению с предыдущим периодом
     * Положительное значение означает рост, отрицательное - снижение
     */
    private Integer attendanceDifference;

    /**
     * Процентное изменение посещаемости по сравнению с предыдущим периодом
     */
    private Double attendanceChangePercentage;
}
