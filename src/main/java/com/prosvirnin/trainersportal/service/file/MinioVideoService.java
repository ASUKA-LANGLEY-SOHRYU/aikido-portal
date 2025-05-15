package com.prosvirnin.trainersportal.service.file;

import io.minio.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioVideoService implements VideoService{

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucketName;

    public StreamingResponseBody getVideoStream(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder().bucket(bucketName).object(filename).build()
        );
        long fileSize = stat.size();

        String range = request.getHeader("Range");
        long start = 0, end = fileSize - 1;

        if (range != null && range.startsWith("bytes=")) {
            String[] parts = range.replace("bytes=", "").split("-");
            start = Long.parseLong(parts[0]);
            if (parts.length > 1 && !parts[1].isEmpty()) {
                end = Long.parseLong(parts[1]);
            }
        }

        long contentLength = end - start + 1;

        response.setHeader("Content-Type", Files.probeContentType(Path.of(filename)));
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Content-Range", String.format("bytes %d-%d/%d", start, end, fileSize));
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        GetObjectResponse object = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .offset(start)
                        .length(contentLength)
                        .build()
        );
        log.debug("111");
        return outputStream -> {
            try (InputStream is = object) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    try {
                        outputStream.write(buffer, 0, bytesRead);
                        outputStream.flush();
                    } catch (IOException e){
                        log.error("Клиент закрыл соединение: ", e);
                        break;
                    }

                }
            } catch (Exception e) {
                log.error("Ошибка! ", e);
            }
        };
    }
}
