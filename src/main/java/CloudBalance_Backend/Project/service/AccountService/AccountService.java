package CloudBalance_Backend.Project.service.AccountService;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.AccountDto.AccountRequest;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@Slf4j
@AllArgsConstructor
public class AccountService {

        private final AccountRepository accountRepository;
        private final UserRepository userRepository;

        public AccountRequest createAccount(AccountRequest request) {
            Account account = new Account();
            account.setAccountName(request.getAccountName());
            account.setAccountId(request.getAccountId());
            account.setArn(request.getArn());
            accountRepository.save(account);
            return request;
        }

        public List<AccountResponse> getAllAccounts() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
               String email = authentication.getName();
               User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return user.getAccounts().stream()
                    .map(account ->
                            new AccountResponse(
                                    account.getId(),
                                    account.getAccountId(),
                                    account.getAccountName(),
                                    account.getArn()
                            )).toList();
            }

            return accountRepository.findAll().stream()
                .map(account ->
                        new AccountResponse(
                                account.getId(),
                                account.getAccountId(),
                                account.getAccountName(),
                                account.getArn()
                        )).toList();
        }
}


