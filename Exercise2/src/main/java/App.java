import java.io.File;

public class App {
    public static void main(String[] args){
        ColumnsManager columnsManager = new ColumnsManager();

        final File folder = new File("C:/Users/Kruppa/Desktop/Testfolder");
        Filereader filereader = new Filereader(folder);
        filereader.getLines();

    }
}
