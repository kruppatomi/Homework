import java.util.List;

public class App {

    public static void main(String[] args){

        ColumnsManager columnsManager = new ColumnsManager();
        //the parameter in the getChangedColumns method is the path directory which you want to scan for .sql files
        List<String> result = columnsManager.getChangedColumns("C:/Users/Kruppa/Desktop/Testfolder");

        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i));
        }
    }
}
