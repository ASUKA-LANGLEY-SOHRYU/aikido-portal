package com.prosvirnin.trainersportal.model.swagger.schema;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {

    @Schema(type = "string", format = "binary", description = "Файл для загрузки")
    private MultipartFile file;
}
