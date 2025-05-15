package com.prosvirnin.trainersportal.service.seminar;

import com.prosvirnin.trainersportal.model.dto.seminar.SeminarCreateRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarEditRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultSeminarService implements SeminarService{
    @Override //TODO: implement
    public Collection<SeminarListItem> findAll() {
        return List.of();
    }

    @Override
    public void save(SeminarCreateRequest request) {

    }

    @Override
    public SeminarDto findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public SeminarDto editById(Long id, SeminarEditRequest request) {
        return null;
    }

    @Override
    public InputStreamResource createInterimReportById(Long id) {
        return null;
    }

    @Override
    public InputStreamResource createFinalReportById(Long id) {
        return null;
    }

    @Override
    public Collection<UserProfileDto> updateUsersByReport(MultipartFile report) {
        return List.of();
    }

    @Override
    public void importInterimReport(MultipartFile interimReport) {

    }
}
