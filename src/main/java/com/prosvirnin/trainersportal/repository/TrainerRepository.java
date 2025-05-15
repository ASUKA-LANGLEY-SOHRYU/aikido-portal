package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.user.TrainerInfo(
            u.id, u.fullName, u.kyu, u.birthDate, u.phoneNumber)
        FROM User u
        JOIN u.roles r
        WHERE u.id = :id AND r = com.prosvirnin.trainersportal.model.domain.user.Role.TRAINER
    """)
    Optional<TrainerInfo> findInfoById(Long id);
}
