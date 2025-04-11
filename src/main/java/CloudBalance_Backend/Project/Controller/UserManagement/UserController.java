package CloudBalance_Backend.Project.Controller.UserManagement;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.UserRequest;
import CloudBalance_Backend.Project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/test")
    public String test(){
        return "Hell0";
    }
//    @PostMapping("/update")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
//        userService.updateUser(userRequest);
//        return ResponseEntity.ok("User updated successfully");
//    }
}
