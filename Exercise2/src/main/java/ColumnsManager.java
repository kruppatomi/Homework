import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColumnsManager {

    List<String> forbiddenStrings;

    public ColumnsManager(){
      forbiddenStrings = getForbiddenStrings();
    }

    //forbidden Substring dwXX
    public void getTableWithoutSubstring(){
        final File folder = new File("C:/Users/Kruppa/Desktop/Testfolder");
        Filereader filereader = new Filereader(folder);
        filereader.getLines();
    }

    public List<String> getForbiddenStrings(){
        List<String> forbidden = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                forbidden.add("dw"+i+j);
            }
        }
        return forbidden;
    }


    public List<String> getForbiddenList(){
        return forbiddenStrings;
    }


}
