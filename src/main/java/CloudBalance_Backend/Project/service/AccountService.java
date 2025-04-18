package CloudBalance_Backend.Project.service;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.dto.AccountDto.AccountRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountService {



        private final AccountRepository accountRepository;

        public AccountService(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        public AccountRequest createAccount(AccountRequest request) {
            Account account = new Account();
            account.setAccountName(request.getAccountName());
            account.setAccountId(request.getAccountId());
            account.setArn(request.getArn());

            accountRepository.save(account);
            return request;
        }
    }


