import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColumnsManager {

    private List<String> forbiddenStrings;

    ColumnsManager(){
      forbiddenStrings = createForbiddenStrings();
    }

    public List<String> createForbiddenStrings(){
        List<String> forbidden = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                forbidden.add("dw"+i+j);
            }
        }
        return forbidden;
    }

    // get tables without forbidden substring in table name
    public List<String> getChangedColumns(String path){
        List<String> changedColumns = new ArrayList<>();
        boolean newTable = false;
        String tableName = "";

        //use file reader class
        final File folder = new File(path);
        FileReader filereader = new FileReader(folder);
        List<String> lines = filereader.getLines();

        //filter lines
        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if (line.contains("ALTER TABLE") && !forbiddenStrings.stream().anyMatch(s -> line.contains(s))){
                String split[] = line.split(" ", 0);
                tableName = split[2];
                newTable = true;
                continue;
            }
            if(newTable && !line.equals(";")){
                String split[] = line.split(" ", 0);
                String columnName = split[1];
                changedColumns.add(tableName + "." + columnName);
            }
            if(line.contains(";")){
                newTable = false;
            }
        }
        return changedColumns;
    }
}
