public class CompareDB {

    public void compare(String oldSchema, String oldTableName){
        //which one is newer??

        // SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'old_schema' AND table_name = 'old_person_table';

        //Which one was deleted?

        //are there new or deleted columns?

        //is there any data type changes in the columns
        String query = "SELECT create_time FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '"+
                oldSchema +"' AND table_name = '"+ oldTableName +"';";
        String result = Database.executeQuery(query);
        System.out.println(result);

    }
}
