package com.prosvirnin.trainersportal.model.domain.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Расписание
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Temporal(value = TemporalType.TIME)
    @Column(name = "start_time")
    private LocalTime startTime;

    @Temporal(value = TemporalType.TIME)
    @Column(name = "end_time")
    private LocalTime endTime;

}
