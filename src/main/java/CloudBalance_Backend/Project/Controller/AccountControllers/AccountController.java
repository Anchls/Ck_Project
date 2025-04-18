package CloudBalance_Backend.Project.Controller.AccountControllers;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.dto.AccountDto.AccountRequest;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import CloudBalance_Backend.Project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/onboarding")
    public ResponseEntity<AccountRequest> createAccount(@RequestBody AccountRequest request) {
        AccountRequest savedAccount = accountService.createAccount(request);
        return ResponseEntity.ok(savedAccount);
    }

}
