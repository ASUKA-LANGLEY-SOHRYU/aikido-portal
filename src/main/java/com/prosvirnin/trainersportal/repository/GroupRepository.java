package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.group.Group;
import com.prosvirnin.trainersportal.model.dto.group.GroupDto;
import com.prosvirnin.trainersportal.model.dto.group.GroupListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Long> findAllByClub_Id(Long clubId);

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.GroupDto(
            g.id, g.name, g.ageGroup,\s
            g.club.address, g.club.id, g.club.name,\s
            g.coach.id, g.coach.fullName)
        FROM Group g""")
    Collection<GroupDto> findAllDto();

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.GroupDto(
            g.id, g.name, g.ageGroup,\s
            g.club.address, g.club.id, g.club.name,\s
            g.coach.id, g.coach.fullName)
        FROM Group g
        WHERE g.id = :id""")
    Optional<GroupDto> findDtoById(Long id);

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.GroupListItem(
            g.id, g.name)
        FROM Group g""")
    Collection<GroupListItem> findAllItems();
}
