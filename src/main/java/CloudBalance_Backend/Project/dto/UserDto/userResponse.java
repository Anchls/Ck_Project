package CloudBalance_Backend.Project.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class userResponse{
        private Long id; // ✅ Add this
        private String username;
        private String email;
        private String role;
        private LocalDateTime lastLogin;
    }


