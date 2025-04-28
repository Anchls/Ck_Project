package CloudBalance_Backend.Project.dto.CostExplorerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostSummary {
    private String name;
    private BigDecimal total;
    private String period;
}