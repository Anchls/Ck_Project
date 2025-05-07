package CloudBalance_Backend.Project.dto.UserDto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDto {
    private Long id;
    private String role;
    private String email;
    private String username;
    private String password;
    private String lastLogin;
    private Set<Long> accountIds;
 }

