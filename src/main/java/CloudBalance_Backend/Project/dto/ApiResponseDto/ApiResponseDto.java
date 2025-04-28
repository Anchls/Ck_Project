package CloudBalance_Backend.Project.dto.ApiResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponseDto {
    private int status;
    private String error;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public static ApiResponseDto error(int status, String error, String message) {
        return new ApiResponseDto(status, error, message, null, LocalDateTime.now());
    }

    public static ApiResponseDto of(int status, String error, String message, Object data) {
        return new ApiResponseDto(status, error, message, data, LocalDateTime.now());
    }

}
