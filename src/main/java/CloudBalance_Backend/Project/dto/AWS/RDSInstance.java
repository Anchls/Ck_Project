package CloudBalance_Backend.Project.dto.AWS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDSInstance {

    private Long resourceId;
    private String resourceName;
    private String engine;
    private String region;
    private String status;
}
