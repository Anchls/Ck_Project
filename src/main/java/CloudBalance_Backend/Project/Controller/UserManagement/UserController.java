package CloudBalance_Backend.Project.Controller.UserManagement;

import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.*;
import CloudBalance_Backend.Project.service.UserService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //send dto instead of entity change for User
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_READ_ONLY')")
    public ResponseEntity<Page<UsersResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUser(page,size));
    }

    @PostMapping("/create-with-accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<userResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        userResponseDto savedUser = userService.createUserWithAccounts(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id) {
        UserDto userDto = userService.getUserWithAccounts(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long userId, @RequestBody updateUserDto request) {
        userService.updateUser(userId, request);
        return ResponseEntity.ok("User updated successfully!");
    }

}
