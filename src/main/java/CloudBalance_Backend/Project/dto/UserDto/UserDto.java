package CloudBalance_Backend.Project.dto.UserDto;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor

public class UserDto {
        private Long id;
        private String username;
        private String role;
        private List<AccountResponse> assignedAccounts;
    }




