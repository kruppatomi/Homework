import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CompareDB {

    public void compare(String oldSchema, String oldTableName, String newSchema, String newTableName){
        //which one is newer??

        System.out.println(getNewTableName(oldSchema, oldTableName, newSchema, newTableName));
        // SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'old_schema' AND table_name = 'old_person_table';

        //Which one was deleted?

        //are there new or deleted columns?

        //is there any data type changes in the columns



    }
    public String getNewTableName(String oldSchema, String oldTableName, String newSchema, String newTableName){
        String query = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                oldSchema +"' AND table_name = '"+ oldTableName +"';";
        String result = Database.executeQuery(query);
        String query2 = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                newSchema +"' AND table_name = '"+ newTableName +"';";
        String result2 = Database.executeQuery(query2);

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
}
