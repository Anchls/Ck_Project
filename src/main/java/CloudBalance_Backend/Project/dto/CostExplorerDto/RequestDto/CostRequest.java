package CloudBalance_Backend.Project.dto.CostExplorerDto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRequest {

    private String groupBy;  //groupby column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private Map<String, List<String>> filters = new HashMap<>();

}