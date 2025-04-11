package CloudBalance_Backend.Project.dto.AuthDto;

import java.time.LocalDateTime;

public class SessionDto {
    private Long id;
    private String accessToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean Validate;
}
