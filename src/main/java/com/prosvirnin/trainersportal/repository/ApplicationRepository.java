package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.application.Application;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationStatus;
import com.prosvirnin.trainersportal.model.dto.application.ApplicationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long > {

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.application.ApplicationDto(
            a.id, a.user.id, a.user.fullName, a.causedBy.fullName, a.type, a.userEditRequest)
        FROM Application a
        WHERE a.status = :status
    """)
    Collection<ApplicationDto> findAllDtoByStatus(ApplicationStatus status);
}
