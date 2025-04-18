package CloudBalance_Backend.Project.Controller.UserManagement;

import CloudBalance_Backend.Project.Entity.Account;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.CreateUserRequest;
import CloudBalance_Backend.Project.dto.UserDto.UserRequest;
import CloudBalance_Backend.Project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> userPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/test")
    public String test() {
        return "Hell0";
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long accountId, @RequestBody UserRequest userRequest) {
        userService.updateUser(accountId, userRequest);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/users/create-with-accounts")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User savedUser = userService.createUserWithAccounts(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/accounts/orphan")
    public ResponseEntity<List<Account>> getOrphanAccounts() {
        return ResponseEntity.ok(userService.getOrphanAccounts());
    }
}

