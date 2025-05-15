package com.prosvirnin.trainersportal.service.user;

import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserFilterRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Set;

/**
 * Логика работы с пользователем
 */
public interface UserService {
    /**
     * Сохранение пользователя
     * @param user пользователь
     */
    void save(User user);

    /**
     * Получение пользователя по id
     * @param id id пользователя
     * @return найденый пользователь.
     * @throws UserNotFoundException если пользователь не найден
     */
    User findById(Long id) throws UserNotFoundException;

    /**
     * Получение пользователя по логину
     * @param login логин пользователя
     * @return найденый пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    User findByLogin(String login) throws UserNotFoundException;

    /**
     * Получение профиля пользователя по UserDetails
     * @param userDetails пользователь
     * @return профиль пользователя
     */
    UserProfileDto getByUserDetails(UserDetails userDetails);

    /**
     * Получение профиля пользователя по id
     * @param id идентификатор пользователя
     * @return профиль пользователя
     */
    UserProfileDto getProfileById(Long id);

    /**
     * Изменяет поля пользователя на те, что пришли в UserEditRequest
     * @param userDetails пользователь для изменения
     * @param userEditRequest запрос на редактирование
     * @return измененный профиль пользователя
     */
    UserProfileDto editByUserDetails(UserDetails userDetails, UserEditRequest userEditRequest);

    /**
     * Изменяет поля пользователя на те, что пришли в UserEditRequest
     * @param id id пользователя для изменения
     * @param userEditRequest запрос на редактирование
     * @return измененный профиль пользователя
     */
    UserProfileDto editById(Long id, UserEditRequest userEditRequest);

    /**
     * Удаление пользователя по id
     * @param id id пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    void deleteById(Long id) throws UserNotFoundException;

    /**
     * Удаление пользователя по логину
     * @param login логин пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    void deleteByLogin(String login) throws UserNotFoundException;

    /**
     * Получить список пользователей с фильтрацией.
     * @param request параметры фильтрации
     * @return список отфильтрованных пользователей
     */
    Collection<UserListItem> findAllListItems(UserFilterRequest request);

    /**
     * Устанавливает роли пользователю
     * @param id идентификатор пользователя
     * @param roles новые роли пользователя
     */
    void setRolesById(Long id,  Set<Role> roles);

    /**
     * Создает клиентов из .xlsx файла
     * @param file .xlsx файл
     */
    void createClientsFromDatasheet(MultipartFile file);
}
