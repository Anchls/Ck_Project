package CloudBalance_Backend.Project.dto.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserManagementRequest {
    private String username;
    private String email;
    private String password;
    private String role;
    private Set<Long> accountIds;
}
