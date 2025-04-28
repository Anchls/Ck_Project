package CloudBalance_Backend.Project.dto.AuthDto;

import CloudBalance_Backend.Project.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//not in user because no signup is available in frontend
@Data
public class SignupRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 12, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Role is required")
    private Role role;

}
