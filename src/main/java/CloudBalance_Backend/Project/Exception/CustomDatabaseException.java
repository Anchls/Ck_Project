package CloudBalance_Backend.Project.Exception;

public class CustomDatabaseException extends RuntimeException {
    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

