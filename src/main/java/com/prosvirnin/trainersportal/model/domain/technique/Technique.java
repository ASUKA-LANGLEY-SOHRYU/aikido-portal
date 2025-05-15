package com.prosvirnin.trainersportal.model.domain.technique;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosvirnin.trainersportal.model.domain.file.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Прием
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Technique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String description;

    /**
     * Видеодемонстрация техники
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @JsonIgnore
    private File techniqueVideoDemonstration;
}
