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
@CrossOrigin(origins = "*")
public class CostExplorerController {

    private final CostExplorerService snowflakeService;

//group by  api  hogi  filter k nam of group by k nam same hai to dono k  lie same api

//    @GetMapping("/usage-summary")
//    public ResponseEntity<Map<String, Object>> getUsageSummaryForAccount(@RequestParam Long accountId) {
//        List<Double> usageAmounts = snowflakeService.getUsageAmountsForAccount(accountId);
//        Map<String, Object> response = new HashMap<>();
//        response.put("accountId", accountId);
//        response.put("usageAmounts", usageAmounts);
//        return ResponseEntity.ok(response);
//    }
////
//    @GetMapping("/filters/columns")
//    public List<String> getAllColumns() {
//        return snowflakeService.getColumnNames();
//    }
//
//    @GetMapping("/filters/values")
//    public List<String> getValuesForColumn(@RequestParam String columnName) {
//        return snowflakeService.getValuesByColumn(columnName);
//    }
//har column k liye distinct value
    @PostMapping("/{accountId}/filter")
    public ResponseEntity<FilterResponse> getDistinctValues(
            @PathVariable Long accountId,
            @RequestBody FilterRequest request) {

        FilterResponse response = snowflakeService.getDistinctValues(accountId, request);
        return ResponseEntity.ok(response);
    }


    //data grouping or multiple filter k sath
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
