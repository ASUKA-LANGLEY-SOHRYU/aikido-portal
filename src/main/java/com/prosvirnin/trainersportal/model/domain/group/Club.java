package com.prosvirnin.trainersportal.model.domain.group;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Клуб
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String address;
}
