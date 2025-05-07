package CloudBalance_Backend.Project.Repository;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.dto.UserDto.UsersResponse;
import CloudBalance_Backend.Project.dto.UserDto.userResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT u.id as id,
            u.name as name,
            u.email as email,
            u.last_login as last_login,
            r.role as role
            FROM users u LEFT JOIN roles r
            ON u.role_id = r.id
            where u.email != :email_id
            """, nativeQuery = true)
    Boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = """
            SELECT u.id as id,
            u.username as username,
            u.email as email,
            u.role as role,
            u.last_login as lastLogin
            FROM users u
            where
            u.email != :email_id
            """,nativeQuery = true)
    Page<UsersResponse> findAllUsers(Pageable pageable,@Param("email_id") String email);


}