package com.prosvirnin.trainersportal.model.domain.group;

import com.prosvirnin.trainersportal.model.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Группа
 */
@Entity
@Table(name = "grp")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    private String name;

    /**
     * Возрастная группа
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ageGroup")
    private AgeGroup ageGroup;
}
