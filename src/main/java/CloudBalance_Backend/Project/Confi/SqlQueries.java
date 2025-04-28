package CloudBalance_Backend.Project.Confi;

public class SqlQueries {
    public static String getDistinctColumnQuery(String columnName) {
        return java.lang.String.format("SELECT DISTINCT %s FROM cost_explorer WHERE %s IS NOT NULL LIMIT 1000", columnName, columnName);
    }




}
