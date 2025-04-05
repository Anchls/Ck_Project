package CloudBalance_Backend.Project.dto;

import CloudBalance_Backend.Project.Entity.Role;
import lombok.Data;
@Data
public class SignupRequest {
    private String username;
    private String password;
    private Role role;
}
