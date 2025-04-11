package CloudBalance_Backend.Project.dto.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementResponse {
private Long userId;
private String username;
private String email;
private Set<AccountRequest> accounts;
}
