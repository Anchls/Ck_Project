package CloudBalance_Backend.Project.Repository;

import CloudBalance_Backend.Project.Entity.CostColumn;
import CloudBalance_Backend.Project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostExplorerRepository extends JpaRepository<CostColumn, Long> {
    Optional<CostColumn> findById(Long id );

}
