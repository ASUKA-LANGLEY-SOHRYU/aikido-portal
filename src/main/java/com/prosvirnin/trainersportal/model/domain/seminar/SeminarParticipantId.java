package com.prosvirnin.trainersportal.model.domain.seminar;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeminarParticipantId implements Serializable {
    private Long user;
    private Long interimReport;
}
