package CloudBalance_Backend.Project.dto.AWS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EC2Instance {

    private String resourceId;
    private String resourceName;
    private String region;
    private String status;
}
