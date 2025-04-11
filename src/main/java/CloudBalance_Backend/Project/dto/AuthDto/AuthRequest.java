package CloudBalance_Backend.Project.dto.AuthDto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;


}