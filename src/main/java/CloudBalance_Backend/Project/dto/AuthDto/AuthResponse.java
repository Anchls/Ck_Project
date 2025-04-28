package CloudBalance_Backend.Project.dto.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String role;
    private String token;

}