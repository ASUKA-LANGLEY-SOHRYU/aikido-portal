package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.exception.api.BadRequest;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.domain.group.ExclusionDate;
import com.prosvirnin.trainersportal.model.domain.group.Group;
import com.prosvirnin.trainersportal.model.domain.group.Schedule;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.group.GroupCreateRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupDto;
import com.prosvirnin.trainersportal.model.dto.group.GroupEditRequest;
import com.prosvirnin.trainersportal.model.dto.group.GroupListItem;
import com.prosvirnin.trainersportal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultGroupService implements GroupService {
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;
    private final ExclusionDateRepository exclusionDateRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    @Override
    public Collection<GroupDto> findAll() {
        return groupRepository.findAllDto();
    }

    @Override
    public Collection<GroupListItem> findAllItems() {
        return groupRepository.findAllItems();
    }

    @Override
    @Transactional
    public GroupDto save(GroupCreateRequest request) {
        User coach = userRepository.findById(request.getCoachId())
                .orElseThrow(() -> new UserNotFoundException(request.getCoachId()));
        Club club = clubRepository.findById(request.getCoachId())
                .orElseThrow(() -> new NotFound("Клуб с id " + request.getCoachId() + " не найден."));

        Group group = Group.builder()
                .name(request.getName())
                .ageGroup(request.getAgeGroup())
                .coach(coach)
                .club(club)
                .build();

        group = groupRepository.save(group);
        Group finalGroup = group;

        Collection<Schedule> schedule = request.getSchedule().stream()
                .map(s -> Schedule.builder()
                        .dayOfWeek(s.getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .group(finalGroup)
                        .build())
                .toList();
        scheduleRepository.saveAll(schedule);

        Collection<ExclusionDate> exclusions = request.getExclusions().stream()
                .map(e -> ExclusionDate.builder()
                        .date(e.getDate())
                        .status(e.getStatus())
                        .group(finalGroup)
                        .build())
                .toList();
        exclusionDateRepository.saveAll(exclusions);

        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .ageGroup(group.getAgeGroup())
                .address(club.getAddress())
                .clubId(club.getId())
                .clubName(club.getName())
                .coachId(coach.getId())
                .coachName(coach.getFullName())
                .build();
    }

    @Override
    @Transactional
    public GroupDto editById(Long id, GroupEditRequest request) {
        User coach = userRepository.findById(request.getCoachId())
                .orElseThrow(() -> new UserNotFoundException(request.getCoachId()));
        Club club = clubRepository.findById(request.getCoachId())
                .orElseThrow(() -> new NotFound("Клуб с id " + request.getCoachId() + " не найден."));
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFound("Группа с id " + id + " не найдена."));

        group.setName(request.getName());
        group.setAgeGroup(request.getAgeGroup());
        group.setClub(club);
        group.setCoach(coach);

        group = groupRepository.save(group);
        Group finalGroup = group;

        scheduleRepository.deleteAllByGroup_Id(id);
        exclusionDateRepository.deleteAllByGroup_Id(id);

        Collection<Schedule> schedule = request.getSchedule().stream()
                .map(s -> Schedule.builder()
                        .dayOfWeek(s.getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .group(finalGroup)
                        .build())
                .toList();
        scheduleRepository.saveAll(schedule);


        Collection<ExclusionDate> exclusions = request.getExclusions().stream()
                .map(e -> ExclusionDate.builder()
                        .date(e.getDate())
                        .status(e.getStatus())
                        .group(finalGroup)
                        .build())
                .toList();
        exclusionDateRepository.saveAll(exclusions);

        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .ageGroup(group.getAgeGroup())
                .address(club.getAddress())
                .clubId(club.getId())
                .clubName(club.getName())
                .coachId(coach.getId())
                .coachName(coach.getFullName())
                .build();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addClientToGroup(Long clientId, Long groupId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));
        if (!client.getRoles().contains(Role.CLIENT))
            throw new BadRequest("Пользователь должен быть учеником.");

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFound("Группа с id " + groupId + " не найдена!"));

        Collection<User> usersInGroup = group.getUsers();
        if (usersInGroup.contains(client))
            throw new BadRequest("Пользователь уже в группе.");

        usersInGroup.add(client);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void removeClientFromGroup(Long clientId, Long groupId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException(clientId));
        if (!client.getRoles().contains(Role.CLIENT))
            throw new BadRequest("Пользователь должен быть учеником.");

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFound("Группа с id " + groupId + " не найдена!"));

        Collection<User> usersInGroup = group.getUsers();
        if (!usersInGroup.contains(client))
            throw new BadRequest("Пользователя и так нет в группе.");

        usersInGroup.remove(client);
        groupRepository.save(group);
    }
}
