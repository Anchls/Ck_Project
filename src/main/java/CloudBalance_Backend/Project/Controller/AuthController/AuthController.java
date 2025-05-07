package CloudBalance_Backend.Project.Controller.AuthController;

import CloudBalance_Backend.Project.Repository.BlackListedTokenRepository;
import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import CloudBalance_Backend.Project.dto.AuthDto.SignupRequest;
import CloudBalance_Backend.Project.service.AuthService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final BlackListedTokenRepository blacklistRepo;

    private final AuthService authService;


   @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }
}
