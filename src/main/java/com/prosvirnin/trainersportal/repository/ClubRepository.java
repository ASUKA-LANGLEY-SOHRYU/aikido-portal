package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.dto.group.ClubInfo;
import com.prosvirnin.trainersportal.model.dto.group.ClubListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query("SELECT new com.prosvirnin.trainersportal.model.dto.group.ClubListItem(c.id, c.name) FROM Club c")
    List<ClubListItem> findAllItems();

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.ClubInfo(
                c.id, c.name, c.address)
        FROM Club c
        WHERE c.id = :id
        """)
    Optional<ClubInfo> findInfoById(Long id);

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.group.ClubInfo(
                c.id, c.name, c.address)
        FROM Club c
        """)
    Collection<ClubInfo> findAllInfo();
}
