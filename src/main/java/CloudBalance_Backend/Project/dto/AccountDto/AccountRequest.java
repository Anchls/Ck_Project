package CloudBalance_Backend.Project.dto.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

    public class AccountRequest{
        private String accountName;
        private String accountID;
        private String arn;
        private String region;
    }

