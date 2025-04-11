package CloudBalance_Backend.Project.dto.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor

public class AccountResponse {
    private Long id;
   private String accountId;
   private String accountName;
    private String arn;
       private String isorphan;
}
