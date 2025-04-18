package CloudBalance_Backend.Project.service;
import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Entity.Role;
import CloudBalance_Backend.Project.Entity.Session;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Exception.UserAlreadyExistsException;
import CloudBalance_Backend.Project.Repository.AccountRepository;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.CreateUserRequest;
import CloudBalance_Backend.Project.dto.UserDto.UserRequest;
import CloudBalance_Backend.Project.dto.UserDto.userResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public void createUser(UserRequest userRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException(userRequest.getEmail());
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.valueOf(userRequest.getRole().toUpperCase()));

        if (userRequest.getAccountIds() != null && !userRequest.getAccountIds().isEmpty()) {
//            Set<Account> accounts = new HashSet<>(accountRepository.findAllById(userRequest.getAccountIds()));
//            user.setAccounts(accounts);
        }

        userRepository.save(user);
    }

    public Page<userResponse> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> {
            LocalDateTime lastLogin = user.getSessions().stream()
                    .map(Session::getLoginTime)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            return new userResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    lastLogin
            );

        });
    }


    public void updateUser(Long accountId, UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.valueOf(userRequest.getRole()));
        userRepository.save(user);
    }


    public User createUserWithAccounts(CreateUserRequest request) {
        // 1. Create and save user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));

        if (Role.valueOf(request.getRole()) == Role.CUSTOMER && request.getAccountIds() != null) {
            List<Account> accounts = accountRepository.findAllByAccountIdIn(request.getAccountIds());

//            for (Account account : accounts) {
//                account.setUser((User) savedUser);
//            }
            accountRepository.saveAll(accounts);
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<Account> getOrphanAccounts() {
//        return accountRepository.findByUserIsNull();
        return null;
    }
}
