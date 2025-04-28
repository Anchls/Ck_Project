package CloudBalance_Backend.Project.Repository;

import CloudBalance_Backend.Project.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);
    Optional<Account> findByAccountId(String accountId);
    List<Account> findAll();
    Account save(Account account);
    List<Account> findAllByAccountIdIn(List<Long> accountIds);


}