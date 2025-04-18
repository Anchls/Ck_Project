package CloudBalance_Backend.Project.dto.UserDto;

import lombok.Data;

import java.util.List;
@Data
public class CreateUserRequest {
        private String username;
        private  String password;
        private String email;
        private String role;
        private List<Long> accountIds;


}
