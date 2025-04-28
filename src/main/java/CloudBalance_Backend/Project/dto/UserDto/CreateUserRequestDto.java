package CloudBalance_Backend.Project.dto.UserDto;

import lombok.Data;

import java.util.List;
//use validations
@Data
public class CreateUserRequestDto {
        private String username;
        private  String password;
        private String email;
        private String role;
        private List<Long> accountIds;


}
