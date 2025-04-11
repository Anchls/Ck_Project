package CloudBalance_Backend.Project.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User Already Exists");
    }

}
