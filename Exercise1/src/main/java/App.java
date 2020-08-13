public class App {

    public static void main(String[] args){
        CompareDB compareDB = new CompareDB();
        //in the parameters add your table's properties
        compareDB.compare("old_schema",   // first table's schema name
                "old_person_table",    // first table's name
                "new_schema",            // second table's schema name
                "new_person_table");  // second table's name
    }
}
