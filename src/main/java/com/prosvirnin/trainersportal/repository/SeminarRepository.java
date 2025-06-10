package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.seminar.Seminar;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarDto;
import com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.seminar.SeminarListItem(
            s.id, s.name, s.start, s.location)
        FROM Seminar s
    """)
    List<SeminarListItem> findAllListItems();

}
