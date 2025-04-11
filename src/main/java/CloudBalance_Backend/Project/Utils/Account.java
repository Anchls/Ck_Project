//package CloudBalance_Backend.Project.Utils;
//
//import CloudBalance_Backend.Project.dto.AccountDto.AccountResponse;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Account {
//    public AccountResponse getAccountResponse(Account account) {
//        if(account==null){
//            return null;
//        }
//        AccountResponse accountResponse = new AccountResponse();
//        accountResponse.setId(account.getId());
//        accountResponse.setAccountId(account.getAccountId());
//        accountResponse.setAccountName(account.getAccountName());
//        accountResponse.setArn(account.getArn());
//        accountResponse.setIsorphan(account.getIsorphan());
//        return accountResponse;
//    }
//    public Account getAccount(AccountResponse accountResponse) {
//        if(accountResponse==null){
//            return null;
//        }
//        Account account = new Account();
//        account.setId(accountResponse.getId());
//        account.setAccountId(accountResponse.getAccountId());
//        account.setAccountName(accountResponse.getAccountName());
//        account.setArn(accountResponse.getArn());
//        account.setIsorphan(accountResponse.getIsorphan());
//        return account;
//    }
//}
