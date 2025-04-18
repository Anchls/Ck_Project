package CloudBalance_Backend.Project.Controller.AccountControllers;
import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.dto.AccountDto.AccountRequest;
import CloudBalance_Backend.Project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/onboarding")
    public ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest request) {
        AccountRequest savedAccount = accountService.createAccount(request);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
