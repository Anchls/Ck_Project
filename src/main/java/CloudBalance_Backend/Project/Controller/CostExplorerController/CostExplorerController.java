package CloudBalance_Backend.Project.Controller.CostExplorerController;
import CloudBalance_Backend.Project.dto.CostExplorerDto.RequestDto.CostRequest;
import CloudBalance_Backend.Project.dto.CostExplorerDto.RequestDto.FilterRequest;
import CloudBalance_Backend.Project.dto.CostExplorerDto.ResponseDto.CostResponse;
import CloudBalance_Backend.Project.dto.CostExplorerDto.ResponseDto.FilterResponse;
import CloudBalance_Backend.Project.service.CostExplorerService.CostExplorerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/snowflake")
@RequiredArgsConstructor
public class CostExplorerController {

    private final CostExplorerService snowflakeService;

    @PostMapping("/{accountId}/filter")
    public ResponseEntity<FilterResponse> getDistinctValues(
            @PathVariable Long accountId,
            @RequestBody FilterRequest request) {

        FilterResponse response = snowflakeService.getDistinctValues(accountId, request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{accountId}")
    public ResponseEntity<CostResponse> getGroupedCosts(
            @PathVariable Long accountId,
            @RequestBody CostRequest request) {

        CostResponse response = snowflakeService.getGroupedCosts(accountId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group")
    public ResponseEntity<Map<String, Object>> getGroupByColumns() {
        Map<String, String> columnMappings = snowflakeService.getColumnMappings();
        Map<String, Object> response = new HashMap<>();
        response.put("GroupBy", columnMappings.keySet());

        return ResponseEntity.ok(response);
    }


}
