package com.prosvirnin.trainersportal.service.technique;

import com.prosvirnin.trainersportal.model.dto.technique.TechniqueCreateRequest;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueDto;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueListItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * Сервис для работы с техниками
 */
public interface TechniqueService {
    /**
     * Сохранить технику
     *
     * @param request запрос на сохранение
     * @param video   видео техники
     * @return объект техники
     */
    TechniqueDto save(TechniqueCreateRequest request, MultipartFile video);

    /**
     * Получить технику по id
     *
     * @param id id техники
     * @return объект техники
     */
    TechniqueDto findById(Long id);

    /**
     * Получить все техники
     *
     * @return коллекция техник
     */
    Collection<TechniqueListItem> findAll();
}
