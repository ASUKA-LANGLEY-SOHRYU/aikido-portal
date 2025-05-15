package com.prosvirnin.trainersportal.repository;

import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByLogin(String login);

    void deleteByLogin(String login);

    boolean existsByLogin(String login);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r = :role")
    long countByRole(@Param("role") Role role);

    @Query("""
        SELECT new com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest(
            u.login, u.password, u.fullName, u.phoneNumber, u.gender,
            u.schoolClass, u.birthDate, u.city, u.parentName, u.kyu, u.attestationDate)
        FROM User u
        WHERE u.id = :id
    """)
    ClientRegistrationRequest getClientRegistrationRequestById(Long id);

    @Query("""
    SELECT u, c
    FROM User u
    LEFT JOIN u.groups g
    LEFT JOIN g.club c
    WHERE (:kyu IS NULL OR u.kyu = :kyu)
      AND (:schoolClass IS NULL OR u.schoolClass = :schoolClass)
      AND (:city IS NULL OR LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%')))
      AND (:clubId IS NULL OR c.id = :clubId)
      AND (:query IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%'))
                          OR LOWER(u.login) LIKE LOWER(CONCAT('%', :query, '%'))
                          OR u.phoneNumber LIKE CONCAT('%', :query, '%'))
""")
    List<Object[]> findUserListItemsByFilter(
            @Param("kyu") Integer kyu,
            @Param("schoolClass") Integer schoolClass,
            @Param("city") String city,
            @Param("clubId") Long clubId,
            @Param("query") String query
    );

    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(u.login) LIKE LOWER(CONCAT('%', :query, '%'))
       OR u.phoneNumber LIKE CONCAT('%', :query, '%')
    """)
    List<User> searchUsers(@Param("query") String query);
}
