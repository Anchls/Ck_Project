package CloudBalance_Backend.Project.dto.UserDto;
import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
@Data
@AllArgsConstructor

public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Role is required")
    private String role;

    private List<AccountResponse> assignedAccounts;
    }




