package com.prosvirnin.trainersportal.service.event;

import com.prosvirnin.trainersportal.model.dto.event.EventCreateRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventDto;
import com.prosvirnin.trainersportal.model.dto.event.EventEditRequest;
import com.prosvirnin.trainersportal.model.dto.event.EventListItem;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Сервис для работы с мероприятиями
 */
public interface EventService {
    /**
     * Создать мероприятие
     * @param request запрос на создание
     * @param file файл, прикрепленный к мероприятию
     * @param author автор мероприятия
     * @return мероприятие
     */
    EventDto save(EventCreateRequest request, MultipartFile file, UserDetails author);

    /**
     * Получение мероприятия по id
     * @param id id мероприятия
     * @return мероприятие
     */
    EventDto findById(Long id);

    /**
     * Получение списка всех мероприятий с базовой информацией.
     * @return список мероприятий
     */
    Collection<EventListItem> findAllListItems();

    /**
     * Получение списка всех мероприятий.
     * @return список мероприятий
     */
    Collection<EventDto> findAll();

    /**
     * Редактирование мероприятия по id
     * @param id id мероприятия
     * @param request запрос на редактирование
     * @param file файл, прикрепленный к мероприятию
     * @return измененное мероприятие
     */
    EventDto editById(Long id, EventEditRequest request, MultipartFile file);

    /**
     * Удаление мероприятия по id
     * @param id id мероприятия
     */
    void deleteById(Long id);
}
