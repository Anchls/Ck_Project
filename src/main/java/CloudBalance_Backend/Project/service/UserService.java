package CloudBalance_Backend.Project.service;
import CloudBalance_Backend.Project.Entity.Role;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.UserDto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final RoleService roleService;
//    private UserRequestEntity userRequestEntity;

    public void createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role role = Role.valueOf(userRequest.getRole().toUpperCase());
        user.setRole(role);
//        user.setRole(userRequest.getRole());
        userRepository.save(user);

    }

//
//    public void updateUser(UserRequest userRequest) {
//        User user = userRepository.findById(userRequest.getId()).orElseThrow(() -> new RuntimeException("User not found"));
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        user.setRole(Role.valueOf(userRequest.getRole()));
//        userRepository.save(user);
//    }


}
