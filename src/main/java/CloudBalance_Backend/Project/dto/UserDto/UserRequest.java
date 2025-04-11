package CloudBalance_Backend.Project.dto.UserDto;

import lombok.Data;
//user table
@Data
public class UserRequest {
    private Long id;
    private String role;
    private String email;
    private String username;
    private String password;
}