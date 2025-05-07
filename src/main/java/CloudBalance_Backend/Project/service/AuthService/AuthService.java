package CloudBalance_Backend.Project.service.AuthService;

import CloudBalance_Backend.Project.Confi.JwtService;
import CloudBalance_Backend.Project.Entity.BlackListedToken;
import CloudBalance_Backend.Project.Exception.CustomException;
import CloudBalance_Backend.Project.Repository.BlackListedTokenRepository;
import CloudBalance_Backend.Project.dto.AuthDto.AuthRequest;
import CloudBalance_Backend.Project.dto.AuthDto.AuthResponse;
import CloudBalance_Backend.Project.dto.AuthDto.SignupRequest;
import CloudBalance_Backend.Project.Entity.User;
import CloudBalance_Backend.Project.Repository.UserRepository;
import CloudBalance_Backend.Project.dto.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
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
    BlackListedTokenRepository blacklistRepo;

    @Autowired
    JwtService jwtService;


    public AuthResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(user.getId(), user.getRole().name(), token);
    }



    public String logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            BlackListedToken blacklistedToken = new BlackListedToken();
            blacklistedToken.setToken(token);
            blacklistedToken.setBlacklistedAt(LocalDateTime.now());
            blacklistRepo.save(blacklistedToken);
        }
        if (token == null || token.isEmpty()) {
            throw new CustomException("Token must be provided", HttpStatus.BAD_REQUEST);
        }

        return "Logout successful";
    }
}