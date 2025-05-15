package com.prosvirnin.trainersportal.service.file;

import com.prosvirnin.trainersportal.exception.api.FileException;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.repository.FileRepository;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MinioFileService implements FileService{

    @Value("${minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public File save(MultipartFile file) {
        String extension = (file.getOriginalFilename() != null && file.getOriginalFilename().contains("."))
                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
                : file.getOriginalFilename();
        String name = UUID.randomUUID() + extension;

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e){
            log.error("Невозможно сохранить файл: ", e);
            return null;
        }

        File fileToSave = File.builder()
                .name(name)
                .path(bucket)
                .type(file.getContentType())
                .build();

        fileRepository.save(fileToSave);

        return fileToSave;
    }

    @Override
    @Transactional
    public boolean delete(String name) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build()
            );
            fileRepository.deleteByName(name);
        } catch (Exception e) {
            log.error("Невозможно удалить файл: ", e);
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public boolean delete(File file) {
        return delete(file.getName());
    }

    @Override
    public InputStreamResource getFileResourceByName(String name) {
        try {
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build()
            );

            return new InputStreamResource(response);
        } catch (Exception e) {
            log.error("Невозможно получить файл: ", e);
            return null;
        }
    }

    @Override
    public InputStreamResource getFileResourceById(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow( () -> new FileException("Неудалось получить файл по id: " + id));
        return getFileResourceByName(file.getName());
    }
}
