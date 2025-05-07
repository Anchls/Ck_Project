package CloudBalance_Backend.Project.Confi;

import CloudBalance_Backend.Project.Entity.BlackListedToken;
import CloudBalance_Backend.Project.Exception.BlackListTokenException;
import CloudBalance_Backend.Project.Repository.BlackListedTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final BlackListedTokenRepository blackListedTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        log.info("token {}",header);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);

        try {
            // check if the token is blacklisted

            Optional<BlackListedToken> blackListedToken = blackListedTokenRepository.findByToken(token);
            if(blackListedToken.isPresent()){
                throw new BlackListTokenException("Invalid Token");
            }

            if (!jwtService.ValidateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            String email = jwtService.getEmailFromToken(token);
            String role = jwtService.getRoleFromToken(token);


            log.info("{} and  {}",email,role);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException
                 | IllegalArgumentException | BlackListTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid or expired JWT token\"}");
            System.out.println("JWT Error: " + e.getMessage());
        }


    }
}