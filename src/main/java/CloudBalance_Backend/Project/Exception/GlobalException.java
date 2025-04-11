package CloudBalance_Backend.Project.Exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userExists(RuntimeException ex){
        return ex.getMessage();
    }

}
