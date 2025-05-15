package com.prosvirnin.trainersportal.service.technique;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.file.File;
import com.prosvirnin.trainersportal.model.domain.technique.Technique;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueCreateRequest;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueDto;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueListItem;
import com.prosvirnin.trainersportal.repository.TechniqueRepository;
import com.prosvirnin.trainersportal.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultTechniqueService implements TechniqueService {

    private final FileService fileService;
    private final TechniqueRepository techniqueRepository;
    private final Mapper<TechniqueCreateRequest, Technique> techniqueCreateRequestTechniqueMapper;
    private final Mapper<Technique, TechniqueDto> techniqueTechniqueDtoMapper;
    private final Mapper<Technique, TechniqueListItem> techniqueTechniqueListItemMapper;

    @Override
    @Transactional
    public TechniqueDto save(TechniqueCreateRequest request, MultipartFile video) {
        Technique technique = techniqueCreateRequestTechniqueMapper.map(request);
        File videoFile = fileService.save(video);
        technique.setTechniqueVideoDemonstration(videoFile);
        technique = techniqueRepository.save(technique);
        return techniqueTechniqueDtoMapper.map(technique);
    }

    @Override
    public TechniqueDto findById(Long id) {
        return techniqueTechniqueDtoMapper.map(techniqueRepository.findById(id)
                .orElseThrow(() -> new NotFound("Technique with id " + id + " not found")));
    }

    @Override
    public Collection<TechniqueListItem> findAll() {
        return techniqueTechniqueListItemMapper.map(techniqueRepository.findAll());
    }
}
