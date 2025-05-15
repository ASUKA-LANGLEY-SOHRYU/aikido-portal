package com.prosvirnin.trainersportal.model.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prosvirnin.trainersportal.model.domain.event.Event;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.group.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Класс, описывающий любого пользователя в системе.
 */
@Entity
@Table(name = "usr")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonIgnore
    private File photo;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    /**
     * Фамилия Имя Отчество
     */
    @Column(name = "full_name")
    private String fullName;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "city")
    private String city;

    /**
     * Кю - ступени в айкидо
     */
    @Column(name = "kyu")
    private Integer kyu;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "attestation_date")
    private LocalDate attestationDate;

    /**
     * Годовой взнос
     */
    @Column(name = "annual_fee")
    private Integer annualFee;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "school_class")
    private Integer schoolClass;

    /**
     * Фамилия Имя Отчество родителя
     */
    @Column(name = "parent_name")
    private String parentName;

    @Column(name = "parent_phone")
    private String parentPhone;

    @OneToMany(mappedBy = "author")
    private List<Event> events;

    private Boolean isApproved;

    @ManyToMany
    @JoinTable(
            name = "student_groups",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }
}
