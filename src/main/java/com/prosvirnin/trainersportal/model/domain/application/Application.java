package com.prosvirnin.trainersportal.model.domain.application;

import com.prosvirnin.trainersportal.core.converter.user.UserEditRequestJsonConverter;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "caused_by_id")
    private User causedBy;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private ApplicationType type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = UserEditRequestJsonConverter.class)
    private UserEditRequest userEditRequest;
}
