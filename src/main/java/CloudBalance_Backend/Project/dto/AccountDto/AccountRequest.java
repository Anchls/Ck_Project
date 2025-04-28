package CloudBalance_Backend.Project.dto.AccountDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequest {

    @NotBlank(message = "Account name is required")
    @Size(min = 3, max = 20, message = "Account name must be between 3 and 50 characters")
    private String accountName;

    @NotNull(message = "Account ID is required")
    @Size(min = 12, max = 12, message = "accountId must be between 3 and 50 characters")
    private String accountId;

    @NotBlank(message = "ARN is required")
    @Size(min = 10, max = 30, message = "ARN must be between 10 and 200 characters")
    private String arn;
}
