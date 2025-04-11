package CloudBalance_Backend.Project.service;

import CloudBalance_Backend.Project.Confi.JwtService;
import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import CloudBalance_Backend.Project.dto.AuthDto.SignupRequest;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor   //handleauth
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;


    public ResponseEntity<?> signup(SignupRequest request) {        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(user.getRole().name(), jwtService.generateToken(user.getUsername(), user.getRole().name())));
    }


    public AuthResponse login(AuthRequest request) {
        System.out.println("here");
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        System.out.println("hello");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate token
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        return new AuthResponse(user.getRole().name(), token);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
        System.out.println("Logged out successfully");
    }
}