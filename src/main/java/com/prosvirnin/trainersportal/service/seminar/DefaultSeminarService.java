package com.prosvirnin.trainersportal.service.seminar;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.seminar.Seminar;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarCreateRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarEditRequest;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import com.prosvirnin.trainersportal.repository.SeminarRepository;
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

    private final SeminarRepository seminarRepository;
    private final Mapper<SeminarCreateRequest, Seminar> seminarCreateRequestSeminarMapper;
    private final Mapper<Seminar, SeminarDto> seminarSeminarDtoMapper;

    @Override
    public Collection<SeminarListItem> findAll() {
        return seminarRepository.findAllListItems();
    }

    @Override
    @Transactional
    public void save(SeminarCreateRequest request) {
        Seminar seminar = seminarCreateRequestSeminarMapper.map(request);
        seminarRepository.save(seminar);
    }

    @Override
    public SeminarDto findById(Long id) {
        return seminarSeminarDtoMapper.map(seminarRepository.findById(id)
                .orElseThrow(() -> new NotFound("Семинар с id " + id + " не найден")));
        //TODO: все возможные ведомости
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        seminarRepository.deleteById(id);
    }

    @Override
    @Transactional
    public SeminarDto editById(Long id, SeminarEditRequest request) {
        Seminar seminar = seminarRepository.findById(id)
                .orElseThrow(() -> new NotFound("Семинар с id " + id + " не найден"));

        seminar.setName(request.getName());
        seminar.setStart(request.getStart());
        seminar.setLocation(request.getLocation());
        seminar.setPrice(request.getPrice());
        seminar.setAnnualFeePrice(request.getAnnualFeePrice());
        seminar.setPassportFeePrice(request.getPassportFeePrice());
        seminar.setAttestationFee5To2Kyu(request.getAttestationFee5To2Kyu());
        seminar.setAttestationFee1Kyu(request.getAttestationFee1Kyu());
        seminar.setAttestationFeeBlackBelt(request.getAttestationFeeBlackBelt());

        seminar = seminarRepository.save(seminar);

        return seminarSeminarDtoMapper.map(seminar);
    }

    @Override
    @Transactional
    public InputStreamResource createInterimReportById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public InputStreamResource createFinalReportById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public Collection<UserProfileDto> updateUsersByReport(MultipartFile report) {
        return List.of();
    }

    @Override
    @Transactional
    public void importInterimReport(MultipartFile interimReport) {

    }
}
