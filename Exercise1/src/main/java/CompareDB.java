import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        String query = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                oldSchema +"' AND table_name = '"+ oldTableName +"';";
        String result = Database.getTableCreationTime(query);
        String query2 = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                newSchema +"' AND table_name = '"+ newTableName +"';";
        String result2 = Database.getTableCreationTime(query2);

        DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.0" );

        LocalDateTime start = LocalDateTime.parse( result , f ) ;
        LocalDateTime stop = LocalDateTime.parse( result2 , f ) ;

        boolean isBefore = start.isBefore( stop ) ;
        if(isBefore){
            return "NEWER TABLE:\n [" + newTableName + "] CREATED AT: " + start.toString().replace('T',' ');
        }
        else {
            return "NEWER TABLE:\n [" + oldTableName + "] CREATED AT: " + stop.toString().replace('T',' ');
        }
    }


    public void compareColumns(String oldSchema, String oldTableName, String newSchema, String newTableName){
        String query = "SELECT column_name, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '"+
                oldSchema +"' AND table_name = '"+ oldTableName +"';";
        String query1 = "SELECT column_name, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '"+
                newSchema +"' AND table_name = '"+ newTableName +"';";
        Map<String, String> oldTablecolumns = Database.getColumnNames(query);
        Map<String, String> newTablecolumns = Database.getColumnNames(query1);

        System.out.println("MODIFICATION(S):");
        for (Map.Entry<String, String> entry : oldTablecolumns.entrySet()) {
            if(newTablecolumns.containsKey(entry.getKey()) && entry.getValue().equals(newTablecolumns.get(entry.getKey()))){
            }
            if(newTablecolumns.containsKey(entry.getKey()) && !entry.getValue().equals(newTablecolumns.get(entry.getKey()))){
                System.out.println(" MODIFIED COLUMN: [" + entry.getKey() + "], OLD DATA TYPE: [" +
                        entry.getValue() + "], NEW DATA TYPE: [" + newTablecolumns.get(entry.getKey()) + "]");
            }
            if(!newTablecolumns.containsKey(entry.getKey())){
                System.out.println(" DELETED COLUMN: [" + entry.getKey() + "]");
            }
        }

        for (Map.Entry<String, String> entry : newTablecolumns.entrySet()) {
            if(!oldTablecolumns.containsKey(entry.getKey())){
                System.out.println(" NEW COLUMN: [" + entry.getKey() + "] DATA TYPE: [" + entry.getValue() + "]");
            }
        }
    }


    public void showComparedTablesNames(String oldTableName, String newTableName){
        System.out.println("COMPARING: \n TABLE: ["  + oldTableName + "] WITH TABLE: [" + newTableName + "]");
    }
}
