package com.prosvirnin.trainersportal.model.domain.seminar;

import com.prosvirnin.trainersportal.model.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Участник семинара
 */
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(SeminarParticipantId.class)
public class SeminarParticipant {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @OneToOne
    @JoinColumn(name = "interim_report_id")
    private InterimReport interimReport;

    /**
     * Кю аттестации
     */
    private String attestationKyu;

    /**
     * Статус подтверждения кю
     */
    private String kyuConfirmationStatus;
}
