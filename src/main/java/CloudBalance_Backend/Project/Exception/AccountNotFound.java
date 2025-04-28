package CloudBalance_Backend.Project.Exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String message) {
        super(message);
    }
}
