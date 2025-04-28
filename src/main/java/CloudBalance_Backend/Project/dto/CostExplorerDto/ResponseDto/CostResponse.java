package CloudBalance_Backend.Project.dto.CostExplorerDto.ResponseDto;

import CloudBalance_Backend.Project.dto.CostExplorerDto.CostSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostResponse {
    private String groupByField;
    private List<CostSummary> data;
    private String dateRange;
}