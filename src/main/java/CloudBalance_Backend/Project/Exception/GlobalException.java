package CloudBalance_Backend.Project.Exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

        @ExceptionHandler(CustomException.class)
        public ResponseEntity<?> handleCustomException(CustomException ex) {
            return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
        }
    }

