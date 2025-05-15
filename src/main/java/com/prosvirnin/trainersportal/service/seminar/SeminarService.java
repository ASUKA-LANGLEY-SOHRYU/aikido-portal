package com.prosvirnin.trainersportal.service.seminar;

import com.prosvirnin.trainersportal.model.dto.seminar.SeminarCreateRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarEditRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Сервис для управления семинарами и взаимодействия с ведомостями (формат XLSX).
 */
public interface SeminarService {

    /**
     * Возвращает список всех семинаров в системе.
     *
     * @return коллекция кратких представлений семинаров
     */
    Collection<SeminarListItem> findAll();

    /**
     * Сохраняет новый семинар на основе переданных данных.
     *
     * @param request объект с данными для создания семинара
     */
    void save(SeminarCreateRequest request);

    /**
     * Возвращает подробную информацию о семинаре по его идентификатору.
     *
     * @param id идентификатор семинара
     * @return DTO с полной информацией о семинаре
     */
    SeminarDto findById(Long id);

    /**
     * Удаляет семинар по его идентификатору.
     *
     * @param id идентификатор удаляемого семинара
     */
    void deleteById(Long id);

    /**
     * Обновляет данные семинара по его идентификатору.
     *
     * @param id идентификатор семинара
     * @param request объект с новыми данными
     * @return обновлённый DTO семинара
     */
    SeminarDto editById(Long id, SeminarEditRequest request);

    /**
     * Создаёт временную (предварительную) ведомость по семинару в формате XLSX.
     *
     * @param id идентификатор семинара
     * @return ресурс с XLSX-файлом
     */
    InputStreamResource createInterimReportById(Long id);

    /**
     * Создаёт итоговую ведомость по семинару в формате XLSX.
     *
     * @param id идентификатор семинара
     * @return ресурс с XLSX-файлом
     */
    InputStreamResource createFinalReportById(Long id);

    /**
     * Обновляет данные пользователей на основе переданной итоговой ведомости.
     *
     * @param report XLSX-файл итоговой ведомости
     * @return список обновлённых пользователей
     */
    Collection<UserProfileDto> updateUsersByReport(MultipartFile report);

    /**
     * Импортирует данные из временной ведомости семинара.
     *
     * @param interimReport XLSX-файл временной ведомости
     */
    void importInterimReport(MultipartFile interimReport);
}
