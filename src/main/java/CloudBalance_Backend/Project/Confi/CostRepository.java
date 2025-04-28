package CloudBalance_Backend.Project.Confi;

import CloudBalance_Backend.Project.dto.CostExplorerDto.CostSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Map of frontend field names to database column names
    private final Map<String, String> columnMapping;

    public CostRepository(@Qualifier("snowflakeJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        // Initialize column mapping with your specific mappings
        this.columnMapping = new HashMap<>();
        columnMapping.put("Service", "PRODUCT_PRODUCTNAME");
        columnMapping.put("Instance Type", "MYCLOUD_INSTANCETYPE");
        columnMapping.put("Account Id", "LINKEDACCOUNTID");
        columnMapping.put("Usage Type", "MYCLOUD_COST_EXPLORER_USAGE_GROUP_TYPE");
        columnMapping.put("Platform", "MYCLOUD_OPERATINGSYSTEM");
        columnMapping.put("Region", "MYCLOUD_REGIONNAME");
        columnMapping.put("Usage Type Group", "LINEITEM_USAGETYPE");
        columnMapping.put("Charge Type", "CHARGE_TYPE");
        columnMapping.put("Operation Type", "LINEITEM_OPERATION");
        columnMapping.put("Purchase Option", "MYCLOUD_PRICINGTYPE");
        columnMapping.put("Database Engine", "PRODUCT_DATABASEENGINE");
        columnMapping.put("Availability Zone", "AVAILABILITYZONE");
        columnMapping.put("Tenancy", "TENANCY");
        columnMapping.put("API Operation", "LINEITEM_OPERATION");
    }


    public List<CostSummary> getGroupedCostsWithFilters(Long accountId, String groupBy,
                                                        LocalDate startDate, LocalDate endDate,
                                                        Map<String, List<String>> filters) {

        // Get the corresponding DB column name for grouping
        String dbColumnName = columnMapping.getOrDefault(groupBy, "PRODUCT_PRODUCTNAME");

        // If no dates provided, use last month as default
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            endDate = now;
            startDate = now.minusMonths(1);
        }

        // Format the period string
        String periodStr = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                " to " +
                endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Create SQL query
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.").append(dbColumnName).append(" as GROUP_KEY, ");

        // Calculate the sum of LINEITEM_USAGEAMOUNT for the selected period with all filters applied
        sql.append("SUM(p.LINEITEM_USAGEAMOUNT) as TOTAL_USAGE ");

        sql.append("FROM \"AWS\".\"COST\".\"COST_EXPLORER\" p ");
        sql.append("WHERE p.LINKEDACCOUNTID = :accountId ");

        // Add date filtering directly to the main WHERE clause
        sql.append("AND p.USAGESTARTDATE BETWEEN :startDate AND :endDate ");

        // Add additional filter conditions if provided
        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                String columnKey = entry.getKey();
                List<String> values = entry.getValue();

                if (values != null && !values.isEmpty() && columnMapping.containsKey(columnKey)) {
                    String filterDbColumnName = columnMapping.get(columnKey);

                    // Build the OR conditions with proper parameter placeholders
                    sql.append("AND (");

                    for (int i = 0; i < values.size(); i++) {
                        if (i > 0) {
                            sql.append(" OR ");
                        }
                        // Use UPPER on both sides to make it case-insensitive
                        sql.append("UPPER(p.").append(filterDbColumnName).append(") = UPPER(:").append(columnKey.replaceAll("\\s+", "_")).append("_").append(i).append(")");
                    }

                    sql.append(") ");
                }
            }
        }

        sql.append("GROUP BY p.").append(dbColumnName).append(" ");
        sql.append("HAVING TOTAL_USAGE > 0 ");  // Only include groups that have data
        sql.append("ORDER BY TOTAL_USAGE DESC");

        // Log the SQL for debugging
        log.debug("Executing SQL: {}", sql.toString());

        // Set parameters
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId.toString());
        params.addValue("startDate", Timestamp.valueOf(startDate.atStartOfDay()));
        params.addValue("endDate", Timestamp.valueOf(endDate.plusDays(1).atStartOfDay()));

        // Add filter parameters
        if (filters != null && !filters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                String columnKey = entry.getKey();
                List<String> values = entry.getValue();

                if (values != null && !values.isEmpty() && columnMapping.containsKey(columnKey)) {
                    String paramPrefix = columnKey.replaceAll("\\s+", "_");
                    for (int i = 0; i < values.size(); i++) {
                        String paramName = paramPrefix + "_" + i;
                        params.addValue(paramName, values.get(i));
                        log.debug("Parameter: {} = {}", paramName, values.get(i));
                    }
                }
            }
        }


        // Execute the query and map results
        return namedParameterJdbcTemplate.query(sql.toString(), params, (rs, rowNum) -> {
            CostSummary summary = new CostSummary();
            summary.setName(rs.getString("GROUP_KEY"));
            BigDecimal totalUsage = rs.getBigDecimal("TOTAL_USAGE");
            summary.setTotal(totalUsage);
            summary.setPeriod(periodStr);
            return summary;
        });
    }


    public List<String> getDistinctValues(Long accountId, String columnName) {
        // Get the corresponding DB column name
        String dbColumnName = columnMapping.getOrDefault(columnName, "PRODUCT_PRODUCTNAME");

        // Create SQL query to get distinct values
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT p.").append(dbColumnName).append(" as DISTINCT_VALUE ");
        sql.append("FROM \"AWS\".\"COST\".\"COST_EXPLORER\" p ");
        sql.append("WHERE p.LINKEDACCOUNTID = :accountId ");
        sql.append("AND p.").append(dbColumnName).append(" IS NOT NULL ");
        sql.append("ORDER BY p.").append(dbColumnName);

        // Set parameters
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId.toString());

        // Execute the query and map results
        return namedParameterJdbcTemplate.query(sql.toString(), params, (rs, rowNum) ->
                rs.getString("DISTINCT_VALUE")
        );
    }

    public Map<String, String> getColumnMapping() {
        return new HashMap<>(columnMapping);
    }
}
