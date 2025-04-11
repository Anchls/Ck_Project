package CloudBalance_Backend.Project.dto.AuthDto;

import CloudBalance_Backend.Project.Entity.Role;
import lombok.Data;
@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
