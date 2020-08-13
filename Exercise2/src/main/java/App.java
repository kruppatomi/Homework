import java.io.File;

public class App {
    public static void main(String[] args){
        ColumnsManager columnsManager = new ColumnsManager();

        Filereader filereader = new Filereader();
        final File folder = new File("your folder path");

        filereader.listFilesForFolder(folder);
    }
}
