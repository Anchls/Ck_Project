package CloudBalance_Backend.Project.Exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.autoscaling.model.AutoScalingException;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.rds.model.RdsException;
import software.amazon.awssdk.services.sts.model.StsException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid Credentials", "Unauthorized");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDuplicateEntry(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Duplicate entry or constraint violation", "Data Integrity Violation");
    }

    @ExceptionHandler(StsException.class)
    public ResponseEntity<Object> handleStsException(StsException ex) {
        return new ResponseEntity<>("Access denied",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<String> badSql(BadSqlGrammarException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> dataAccessException(DataAccessException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not able to access data");
    }

    @ExceptionHandler(Ec2Exception.class)
    public ResponseEntity<String> ec2Exception(Ec2Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to get Ec2 Data");
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<String> invalidCreds(InvalidCredentials ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AutoScalingException.class)
    public ResponseEntity<String> asgException(AutoScalingException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to get Asg Data");
    }

    @ExceptionHandler(RdsException.class)
    public ResponseEntity<String> rdsException(RdsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to get Rds Data");
    }

    @ExceptionHandler(SdkException.class)
    public ResponseEntity<String> sdkException(SdkException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected Error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArguments(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Add this handler for BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public  ResponseEntity<String> handleBadRequest(InternalAuthenticationServiceException ex){
        return new ResponseEntity<>("Bad credentials",HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountNotFound.class)
    public  ResponseEntity<String> handleAccountNotFound(AccountNotFound ex){
        return new ResponseEntity<>("Account Not Found",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public  ResponseEntity<String> handleAccountNotFound(AuthorizationDeniedException ex){
        return new ResponseEntity<>("Access denied",HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", Instant.now());
        return new ResponseEntity<>(response, status);
    }
}
