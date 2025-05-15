package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.service.file.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/v1/videos")
@Tag(name = "Видео", description = "Работа с видео файлами")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Operation(summary = "Получить стриминг видео файла")
    @GetMapping("/{filename}")
    public ResponseEntity<StreamingResponseBody> streamVideo(
            @PathVariable String filename,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        StreamingResponseBody body = videoService.getVideoStream(filename, request, response);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(body);
    }
}
