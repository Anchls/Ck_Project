package CloudBalance_Backend.Project.Controller.AuthController;

import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import CloudBalance_Backend.Project.dto.AuthDto.SignupRequest;
import CloudBalance_Backend.Project.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        System.out.println("hello");
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        System.out.println("login success");
        return authService.login(request);
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully!";
    }
}
