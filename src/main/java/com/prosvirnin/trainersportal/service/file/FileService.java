package com.prosvirnin.trainersportal.service.file;

import com.prosvirnin.trainersportal.model.domain.file.File;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с файлами
 */
public interface FileService {

    /**
     * Сохраняет файл и возвращает объект сохраненного файла
     *
     * @param file файл
     * @return объект файла
     */
    File save(MultipartFile file);

    /**
     * Удаляет файл по названию
     *
     * @param name название файла
     * @return true - файл удален, false - файл не удален.
     */
    boolean delete(String name);

    /**
     * Удаляет файл по объекту файла
     *
     * @param file объект файла
     * @return true - файл удален, false - файл не удален.
     */
    boolean delete(File file);

    /**
     * Получение массива байт файла по названию
     *
     * @param name название файла
     * @return массив байт файла
     */
    InputStreamResource getFileResourceByName(String name);

    /**
     * Получение массива байт файла по id
     *
     * @param id идентификатор {@link File}
     * @return массив байт файла
     */
    InputStreamResource getFileResourceById(Long id);

}
