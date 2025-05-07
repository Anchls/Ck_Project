package CloudBalance_Backend.Project.service.CostExplorerService;

import CloudBalance_Backend.Project.Confi.CostRepository;
import CloudBalance_Backend.Project.dto.CostExplorerDto.CostSummary;
import CloudBalance_Backend.Project.dto.CostExplorerDto.RequestDto.CostRequest;
import CloudBalance_Backend.Project.dto.CostExplorerDto.RequestDto.FilterRequest;
import CloudBalance_Backend.Project.dto.CostExplorerDto.ResponseDto.CostResponse;
import CloudBalance_Backend.Project.dto.CostExplorerDto.ResponseDto.FilterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CostExplorerService {

    private final CostRepository costRepository;

    public CostExplorerService(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    public CostResponse getGroupedCosts(Long accountId, CostRequest request) {
        log.info("Getting cost data for account {} grouped by {} with filters: {}",
                accountId, request.getGroupBy(), request.getFilters());

        String groupBy = request.getGroupBy();
        if (groupBy == null || groupBy.trim().isEmpty()) {
            groupBy = "Service";
        }

        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            endDate = now;
            startDate = now.minusMonths(1);
        }

        String dateRange = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                " to " + endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Map<String, List<String>> validatedFilters = new HashMap<>();
        if (request.getFilters() != null) {
            Map<String, String> columnMappings = costRepository.getColumnMapping();
            request.getFilters().forEach((key, values) -> {
                if (columnMappings.containsKey(key) && values != null && !values.isEmpty()) {
                    validatedFilters.put(key, values);
                } else {
                    log.warn("Ignoring invalid filter: {} with values: {}", key, values);
                }
            });
        }

        List<CostSummary> summaries;
        try {
            summaries = costRepository.getGroupedCostsWithFilters(
                    accountId, groupBy, startDate, endDate, validatedFilters);
            log.info("Query returned {} results", summaries.size());
        } catch (Exception e) {
            log.error("Error retrieving cost data: {}", e.getMessage(), e);
            summaries = List.of();
        }

        CostResponse response = new CostResponse();
        response.setGroupByField(groupBy);
        response.setData(summaries);
        response.setDateRange(dateRange);

        return response;
    }

    public FilterResponse getDistinctValues(Long accountId, FilterRequest request) {
        log.info("Getting distinct values for column {} in account {}", request.getColumnName(), accountId);
        String columnName = request.getColumnName();
        if (columnName == null || columnName.trim().isEmpty()) {
            columnName = "Service";
        }
        if (!costRepository.getColumnMapping().containsKey(columnName)) {
            log.warn("Invalid column name requested: {}", columnName);
            return new FilterResponse(columnName, List.of());
        }

        List<String> values = costRepository.getDistinctValues(accountId, columnName);
        FilterResponse response = new FilterResponse();
        response.setColumnName(columnName);
        response.setValues(values);
        return response;
    }

    public Map<String, String> getColumnMappings() {
        log.info("Getting all column mappings for grouping");
        return costRepository.getColumnMapping();
    }
}
