package CloudBalance_Backend.Project.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//validation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateUserDto {
    private String username;
    private String role;
    private List<Long> assignAccountIds;
}
