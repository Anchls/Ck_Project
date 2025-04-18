package CloudBalance_Backend.Project.Repository;

import CloudBalance_Backend.Project.Entity.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken, Long> {
    BlackListedToken findByToken(String token);
}