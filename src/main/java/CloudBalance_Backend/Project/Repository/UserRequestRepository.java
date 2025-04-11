package CloudBalance_Backend.Project.Repository;

import CloudBalance_Backend.Project.Entity.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRequestRepository  extends JpaRepository<UserRequestEntity, Long> {

}
