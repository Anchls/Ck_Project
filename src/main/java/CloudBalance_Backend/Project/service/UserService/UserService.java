package CloudBalance_Backend.Project.service.UserService;
import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Entity.Role;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import CloudBalance_Backend.Project.dto.UserDto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;


    public userResponseDto createUserWithAccounts(CreateUserRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        if (user.getRole() == Role.CUSTOMER && request.getAccountIds() != null) {
            List<Account> accounts = accountRepository.findAllByAccountIdIn(request.getAccountIds());
            user.setAccounts(accounts);
        }

        User savedUser = userRepository.save(user);

        return new userResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
               savedUser.getLastLogin()
//                savedUser.getCreatedAt()
        );
    }



    @Transactional
    public void updateUser(Long userId, updateUserDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(request.getUsername());
        try {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
            if (Role.valueOf(request.getRole()) == Role.CUSTOMER && request.getAssignAccountIds() != null) {
                List<Account> accounts = accountRepository.findAllByAccountIdIn(request.getAssignAccountIds());
                user.setAccounts(accounts);
            } else {
                user.setAccounts(null);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
           }

        userRepository.save(user);
    }

    public UserDto getUserWithAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<AccountResponse> accountDtos = user.getAccounts().stream()
                .map(account -> {
                    AccountResponse dto = new AccountResponse();
                    dto.setId(account.getId());
                    dto.setAccountId(account.getAccountId());
                    dto.setAccountName(account.getAccountName());
                    dto.setArn(account.getArn());
                    return dto;
                }).collect(Collectors.toList());

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole().name(),
                accountDtos
        );
    }
}


