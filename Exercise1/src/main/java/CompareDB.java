import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CompareDB {

    public void compare(String oldSchema, String oldTableName, String newSchema, String newTableName){

        compareColumns(oldSchema, oldTableName, newSchema, newTableName);

        //which one is newer??
        System.out.println(getNewTableName(oldSchema, oldTableName, newSchema, newTableName));

        //Which one was deleted?
/*        System.out.println(getDeletedTable());*/

        //are there new or deleted columns?

        //is there any data type changes in the columns




    }
    //hibák kezelése ha hibás az sql query
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
            return newTableName + " is newer than: " + oldTableName;
        }
        else {
            return oldTableName + " is newer than: " + newTableName;
        }
    }


/*    public String getDeletedTable(){
        String query2 = "SELECT delete_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                "homework" + "';";
        String result = Database.executeQuery(query2);
        return result;
    }*/

    public void compareColumns(String oldSchema, String oldTableName, String newSchema, String newTableName){
        //ennek a querynek a resultjából kell nekem a column_name és a data_type oszlop

        String query = "SELECT column_name, data_type FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '"+
                newSchema +"' AND table_name = '"+ newTableName +"';";
        Map<String, String> columnsResponse = Database.getColumnNames(query);
    }
}
