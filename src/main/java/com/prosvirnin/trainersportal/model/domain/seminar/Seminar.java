package com.prosvirnin.trainersportal.model.domain.seminar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosvirnin.trainersportal.model.domain.file.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Семинар
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seminar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    /**
     * Дата начала семинара
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "start")
    private LocalDateTime start;

    /**
     * Место проведения
     */
    private String location;

    /**
     * Цена
     */
    private String price;

    /**
     * Цена за годовой взнос
     */
    private String annualFeePrice;

    /**
     * Цена за будо-пасспорт
     */
    private String passportFeePrice;

    /**
     * Цена за аттестацию на 5 - 2 кю
     */
    private String attestationFee5To2Kyu;

    /**
     * Цена за аттестацию на 1 кю
     */
    private String attestationFee1Kyu;

    /**
     * Цена за аттестацию на черный пояс
     */
    private String attestationFeeBlackBelt;

    /**
     * Итоговая ведомость
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @JsonIgnore
    private File finalReport;
}
