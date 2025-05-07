package CloudBalance_Backend.Project.service.UserService;

import CloudBalance_Backend.Project.Exception.BadRequestException;
import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Entity.Role;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import CloudBalance_Backend.Project.dto.UserDto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Page<UsersResponse> getAllUser(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        Page<UsersResponse> userPage = userRepository.findAllUsers(pageable, authentication.getPrincipal().toString());
        return userPage;
    }

    public userResponseDto createUserWithAccounts(CreateUserRequestDto request) {

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + request.getRole());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        if (role == Role.CUSTOMER && request.getAccountIds() != null) {
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
        );
    }

    @Transactional
    public void updateUser(Long userId, updateUserDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

        user.setUsername(request.getUsername());
        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + request.getRole());
        }
        user.setRole(role);

        if (role == Role.CUSTOMER && request.getAccountIds() != null) {
            List<Account> accounts = accountRepository.findAllByAccountIdIn(request.getAccountIds());
            user.setAccounts(accounts);
        } else {
            user.setAccounts(null);
        }
        userRepository.save(user);
    }

    public UserDto getUserWithAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with ID: " + userId));

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
