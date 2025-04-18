package CloudBalance_Backend.Project.Exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("User Already Exists with email: " + email);
        log.warn("Attempt to create duplicate user with email: {}", email);
    }

    public UserAlreadyExistsException() {
        super("User Already Exists");
        log.warn("Attempt to create duplicate user (email not provided)");
    }
}
