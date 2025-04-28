package CloudBalance_Backend.Project.Controller.UserManagement;

import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.*;
import CloudBalance_Backend.Project.service.UserService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "30") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> userPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(userPage);
    }

    @PostMapping("/users/create-with-accounts")
    public ResponseEntity<userResponseDto> createUser(@RequestBody CreateUserRequestDto request) {
        userResponseDto savedUser = userService.createUserWithAccounts(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable Long id) {
        UserDto userDto = userService.getUserWithAccounts(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody updateUserDto request) {
        userService.updateUser(userId, request);
        return ResponseEntity.ok("User updated successfully!");
    }

}
