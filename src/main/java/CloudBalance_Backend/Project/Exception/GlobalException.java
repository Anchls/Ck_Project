package CloudBalance_Backend.Project.Exception;
import CloudBalance_Backend.Project.dto.ApiResponseDto.ApiResponseDto;
import net.snowflake.client.jdbc.internal.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException{

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseDto> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", ex.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Validation Error", "Invalid inputs", fieldErrors));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponseDto.error(ex.getStatus().value(), ex.getStatus().getReasonPhrase(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(HttpStatus.BAD_REQUEST.value(), "Bad Request", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleAllUncaughtException(Exception ex) {
        ex.printStackTrace(); // Helpful in development
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", ex.getMessage()));
    }
}

