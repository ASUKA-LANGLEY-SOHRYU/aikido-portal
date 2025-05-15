package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.dto.group.ClubCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubInfo;
import com.prosvirnin.trainersportal.model.dto.group.ClubListItem;
import com.prosvirnin.trainersportal.repository.ClubRepository;
import com.prosvirnin.trainersportal.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultClubService implements ClubService {
    private final ClubRepository clubRepository;
    private final GroupRepository groupRepository;
    private final Mapper<ClubCreateRequest, Club> clubCreateRequestClubMapper;

    @Override
    public ClubInfo getById(Long id) {
        ClubInfo clubInfo = clubRepository.findInfoById(id)
                .orElseThrow(() -> new NotFound("Клуб с id " + id + " Не найден"));

        Collection<Long> groups = groupRepository.findAllByClub_Id(clubInfo.getId());
        clubInfo.setGroups(groups);

        return clubInfo;
    }

    @Override
    public Collection<ClubListItem> getAllListItems() {
        return clubRepository.findAllItems();
    }

    @Override
    public Collection<ClubInfo> getAll() {
        return clubRepository.findAllInfo();
    }

    @Override
    @Transactional
    public ClubInfo save(ClubCreateRequest request) {
        Club club = clubCreateRequestClubMapper.map(request);
        club = clubRepository.save(club);
        return ClubInfo.builder()
                .name(club.getName())
                .id(club.getId())
                .address(club.getAddress())
                .groups(Collections.emptyList())
                .build();
    }

    @Override
    @Transactional
    public ClubInfo editById(Long id, ClubEditRequest request) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new NotFound("Клуб с id " + id + " не найден"));
        editClub(club, request);
        clubRepository.save(club);
        return clubRepository.findInfoById(id)
                .orElseThrow(() -> new NotFound("Клуб с id " + id + " не найден"));
    }

    @Override
    @Transactional
    public void deleteWithGroupsById(Long id) {
        ClubInfo clubInfo = clubRepository.findInfoById(id)
                .orElseThrow(() -> new NotFound("Клуб с id " + id + " не найден"));
        groupRepository.deleteAllByIdInBatch(clubInfo.getGroups());
        clubRepository.deleteById(id);
    }

    private void editClub(Club club, ClubEditRequest request) {
        club.setName(request.getName());
        club.setAddress(request.getAddress());
    }
}
