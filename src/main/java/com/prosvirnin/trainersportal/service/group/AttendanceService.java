package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.dto.group.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Сервис для управления посещаемостью тренировок.
 * Предоставляет функциональность по отметке присутствия и получению журналов.
 */
public interface AttendanceService {

    /**
     * Отмечает посещение конкретного ученика на определённую дату.
     *
     * @param request запрос на отметку посещения {@link AttendanceMarkRequest}
     * @return DTO с результатами отметки {@link AttendanceDto}
     */
    AttendanceDto markAttendance(AttendanceMarkRequest request);

    /**
     * Получает журнал посещаемости для клиента (ученика) по заданному периоду и фильтру.
     *
     * @param request фильтр для журнала {@link JournalRequest}
     * @return коллекция DTO с данными по группам и посещениям {@link JournalForClientDto}
     */
    Collection<JournalForClientDto> getJournalForClient(UserDetails userDetails, JournalRequest request);

    /**
     * Получает журнал посещаемости для тренера с информацией по ученикам, группам и посещениям.
     *
     * @param request фильтр для журнала {@link JournalRequest}
     * @return коллекция DTO с данными по клиентам и группам {@link JournalForTrainerDto}
     */
    Collection<JournalForTrainerDto> getJournalForTrainer(JournalRequest request);
}
