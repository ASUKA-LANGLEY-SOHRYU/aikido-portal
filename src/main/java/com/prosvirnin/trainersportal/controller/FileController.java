package com.prosvirnin.trainersportal.controller;

import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@Tag(name = "Файлы", description = "Загрузка и скачивание файлов")
public class FileController {

    private final FileService fileService;

    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024 * 5;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "video/mp4",
            "video/webm",
            "application/pdf",
            "application/zip"
    );

    @Operation(
            summary = "Загрузить файл",
            description = """
                    Загружает файл и возвращает его URL.
                    Поддерживаемые типы: jpeg, png, webp, mp4, webm, pdf, zip.
                    Максимальный размер файла: 100MB.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен (возвращается URL)"),
            @ApiResponse(responseCode = "400", description = "Файл пустой, превышен размер или тип не поддерживается")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Файл для загрузки", required = true)
            @RequestParam("file") MultipartFile file) {

        String contentType = file.getContentType();
        long size = file.getSize();

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл пустой.");
        }

        if (size > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body("Файл слишком большой. Максимум 100MB.");
        }

        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest().body("Неподдерживаемый тип файла: " + contentType);
        }

        File savedFile = fileService.save(file);
        String fileUrl = String.format("http://localhost:9000/%s/%s", savedFile.getPath(), savedFile.getName());

        return ResponseEntity.ok(fileUrl);
    }

    @Operation(
            summary = "Скачать файл по имени",
            description = "Скачивает файл по имени (filename) через параметр запроса."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Файл успешно найден и возвращён"),
            @ApiResponse(responseCode = "404", description = "Файл не найден")
    })
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @Parameter(description = "Имя файла", required = true)
            @RequestParam("filename") String fileName) {

        InputStreamResource file = fileService.getFileResourceByName(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @Operation(
            summary = "Скачать файл по ID",
            description = "Скачивает файл по ID из хранилища Minio и возвращает в виде потока."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Файл успешно найден и возвращён"),
            @ApiResponse(responseCode = "404", description = "Файл не найден")
    })
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(
            @Parameter(description = "ID файла", required = true)
            @PathVariable("id") Long id) {

        InputStreamResource file = fileService.getFileResourceById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }
}