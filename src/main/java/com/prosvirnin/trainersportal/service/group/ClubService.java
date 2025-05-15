package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.dto.group.ClubCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.ClubInfo;
import com.prosvirnin.trainersportal.model.dto.group.ClubListItem;

import java.util.Collection;

/**
 * Сервис для работы с клубами
 */
public interface ClubService {
    ClubInfo getById(Long id);

    Collection<ClubListItem> getAllListItems();

    Collection<ClubInfo> getAll();

    ClubInfo save(ClubCreateRequest request);

    ClubInfo editById(Long id, ClubEditRequest request);

    void deleteWithGroupsById(Long id);
}
