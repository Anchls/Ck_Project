package CloudBalance_Backend.Project.Confi;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private final String key = "afdskbfdskskjvffvjkvvfd,mfvn,dsm,alkdjvnvkfdjvnvk";
    private final long EXPIRATION = 15 * 60 * 60 * 1000; // 15 minutes

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String generateToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
     public boolean ValidateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token is invalid: {}", e.getMessage());
        } catch (RuntimeException e) {
            log.error("JWT token is malformed: {}", e.getMessage());
        }
      return false;
     }
     public String getRoleFromToken(String token) {
         return Jwts.parserBuilder()
                 .setSigningKey(getKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .get("role", String.class);
     }

     public String getUsernameFromToken(String token) {
         return Jwts.parserBuilder()
                 .setSigningKey(getKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
     }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}