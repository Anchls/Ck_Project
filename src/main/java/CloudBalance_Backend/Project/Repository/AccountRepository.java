package CloudBalance_Backend.Project.Repository;

import CloudBalance_Backend.Project.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);
    List<Account> findAll();
//    List<Account> findByUserIsNull();
//    List<Account> findByIdIn(List<String> ids);

    void deleteById(Long id);
    Account save(Account account);

    List<Account> findAllByAccountIdIn(List<Long> accountIds);
}