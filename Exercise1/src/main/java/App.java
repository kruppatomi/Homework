public class App {

    public static void main(String[] args){

        CompareDB compareDB = new CompareDB();
        compareDB.compare("old_schema", "old_person_table");

    }
}
