package com.prosvirnin.trainersportal.model.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Мероприятие
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "city")
    private String city;

    @Column(name = "name")
    private String name;

    /**
     * Дата начала мероприятия
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "description")
    private String description;

    /**
     * Файл, приложенный к мероприятию
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @JsonIgnore
    private File file;
}
