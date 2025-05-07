package CloudBalance_Backend.Project.dto.UserDto;

import lombok.Data;

import java.util.List;
//use validations
@Data
public class CreateUserRequestDto {
        private String username;
        private String role;
        private String email;
        private String password;
        private List<Long> accountIds;
}
