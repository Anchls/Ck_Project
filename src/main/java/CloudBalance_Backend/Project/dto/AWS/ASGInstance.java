package CloudBalance_Backend.Project.dto.AWS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ASGInstance {

    private String resourceId;
    private String resourceName;
    private String region;
    private String desiredCapacity;
    private String minSize;
    private String maxSize;
    private String status;
}
