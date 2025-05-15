package com.prosvirnin.trainersportal.service.file;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Сервис для работы с видео файлами
 */
public interface VideoService {
    /**
     * Получение видео потока
     * @param filename название файла
     * @param request запрос
     * @param response ответ
     * @return видео поток
     */
    StreamingResponseBody getVideoStream(String filename,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception;
}
