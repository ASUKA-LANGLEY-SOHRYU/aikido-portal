package com.prosvirnin.trainersportal.service.application;

import com.prosvirnin.trainersportal.model.dto.application.ApplicationDto;
import com.prosvirnin.trainersportal.model.dto.application.CreateApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.application.ReplyApplicationRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Сервис для работы с заявками.
 * Позволяет создавать, обрабатывать и просматривать заявки, находящиеся в процессе рассмотрения.
 */
public interface ApplicationService {

    /**
     * Возвращает все заявки, находящиеся в статусе "IN_PROGRESS".
     *
     * @return список DTO заявок в работе {@link ApplicationDto}
     */
    Collection<ApplicationDto> findAllInProgressApplications();

    /**
     * Осуществляет отклик на заявку по её идентификатору.
     *
     * @param id идентификатор заявки
     * @param request тело отклика на заявку {@link ReplyApplicationRequest}
     */
    void replyById(Long id, ReplyApplicationRequest request);

    /**
     * Создаёт новую заявку от имени пользователя.
     *
     * @param causedBy текущий пользователь, инициировавший создание
     * @param request данные новой заявки {@link CreateApplicationRequest}
     */
    void createApplication(UserDetails causedBy, CreateApplicationRequest request);
}
