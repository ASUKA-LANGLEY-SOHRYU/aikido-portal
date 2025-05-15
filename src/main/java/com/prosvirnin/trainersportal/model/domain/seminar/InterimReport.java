package com.prosvirnin.trainersportal.model.domain.seminar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Промежуточная ведомость
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterimReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seminar_id")
    private Seminar seminar;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    /**
     * Промежуточная ведомость
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @JsonIgnore
    private File interimReport;
}
