import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CompareDB {

    public void compare(String oldSchema, String oldTableName, String newSchema, String newTableName){
        //Display tables to compare
        showComparedTablesNames(oldTableName, newTableName);
        //Compare columns
                //-deleted columns
                //-new columns
                //-data type change
        compareColumns(oldSchema, oldTableName, newSchema, newTableName);
        //which one is newer??
        System.out.println(getNewTableName(oldSchema, oldTableName, newSchema, newTableName));
        //Which table was deleted? ---> backlog
    }


    public String getNewTableName(String oldSchema, String oldTableName, String newSchema, String newTableName){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
        LocalDateTime start = LocalDateTime.parse(getCreationTime(oldSchema, oldTableName), f);
        LocalDateTime stop = LocalDateTime.parse(getCreationTime(newSchema, newTableName), f);
        boolean isBefore = start.isBefore( stop );

        if(isBefore){
            return "NEWER TABLE:\n [" + newTableName + "] CREATED AT: " + start.toString().replace('T',' ');
        }
        else {
            return "NEWER TABLE:\n [" + oldTableName + "] CREATED AT: " + stop.toString().replace('T',' ');
        }
    }


    public String getCreationTime(String schemaName, String tableName){
        String query = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                schemaName +"' AND table_name = '"+ tableName +"';";
         return Database.getTableCreationTime(query);
    }


    public void compareColumns(String oldSchema, String oldTableName, String newSchema, String newTableName){
        Map<String, String> oldTablecolumns = getColumns(oldSchema, oldTableName);
        Map<String, String> newTablecolumns = getColumns(newSchema, newTableName);
        System.out.println("MODIFICATION(S) IN [" + newTableName + "]:");
        //check old table's keys and values
        for (Map.Entry<String, String> entry : oldTablecolumns.entrySet()) {
            if(newTablecolumns.containsKey(entry.getKey()) && entry.getValue().equals(newTablecolumns.get(entry.getKey()))){
                continue;
            }
            if(newTablecolumns.containsKey(entry.getKey()) && !entry.getValue().equals(newTablecolumns.get(entry.getKey()))){
                System.out.println(" MODIFIED COLUMN: [" + entry.getKey() + "], OLD DATA TYPE: [" +
                        entry.getValue() + "], NEW DATA TYPE: [" + newTablecolumns.get(entry.getKey()) + "]");
            }
            if(!newTablecolumns.containsKey(entry.getKey())){
                System.out.println(" DELETED COLUMN: [" + entry.getKey() + "]");
            }
            else{
                continue;
            }
        }
        //check new table's keys and values
        for (Map.Entry<String, String> entry : newTablecolumns.entrySet()) {
            if(!oldTablecolumns.containsKey(entry.getKey())){
                System.out.println(" NEW COLUMN: [" + entry.getKey() + "] DATA TYPE: [" + entry.getValue() + "]");
            }
            else{
                continue;
            }
        }
    }


    public Map<String, String> getColumns(String schemaName, String tableName){
        String query = "SELECT column_name, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '"+
                schemaName +"' AND table_name = '"+ tableName +"';";
        return Database.getColumnNames(query);
    }


    public void showComparedTablesNames(String oldTableName, String newTableName){
        System.out.println("COMPARING: \n TABLE: ["  + oldTableName + "] WITH TABLE: [" + newTableName + "]");
    }
}
