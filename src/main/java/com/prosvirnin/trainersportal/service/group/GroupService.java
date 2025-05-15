package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.dto.group.GroupCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupDto;
import com.prosvirnin.trainersportal.model.dto.group.GroupEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupListItem;

import java.util.Collection;

/**
 * Сервис для управления группами в системе.
 */
public interface GroupService {

    /**
     * Получить список всех групп с полной информацией.
     *
     * @return коллекция объектов {@link GroupDto}
     */
    Collection<GroupDto> findAll();

    /**
     * Получить краткий список всех групп (например, для выпадающих списков).
     *
     * @return коллекция объектов {@link GroupListItem}
     */
    Collection<GroupListItem> findAllItems();

    /**
     * Создать новую группу на основе запроса.
     *
     * @param request объект с данными для создания {@link GroupCreateRequest}
     * @return созданная группа в виде {@link GroupDto}
     */
    GroupDto save(GroupCreateRequest request);

    /**
     * Отредактировать существующую группу по её идентификатору.
     *
     * @param id идентификатор группы
     * @param request объект с обновлёнными данными {@link GroupEditRequest}
     * @return обновлённая группа в виде {@link GroupDto}
     */
    GroupDto editById(Long id, GroupEditRequest request);

    /**
     * Удалить группу по её идентификатору.
     *
     * @param id идентификатор удаляемой группы
     */
    void deleteById(Long id);

    /**
     * Добавляет ученика в группу
     * @param clientId идентификатор ученика
     * @param groupId идентификатор группы
     */
    void addClientToGroup(Long clientId, Long groupId);

    /**
     * Удаляет ученика из группы
     * @param clientId идентификатор ученика
     * @param groupId идентификатор группы
     */
    void removeClientFromGroup(Long clientId, Long groupId);
}
