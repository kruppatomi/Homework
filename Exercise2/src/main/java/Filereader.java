import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Filereader {

    private List<String> result = new ArrayList<>();

    public Filereader(final File folder){
        listFilesFromFolder(folder);
    }

    public void listFilesFromFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesFromFolder(fileEntry);
            }
            else {
                if(fileEntry.isFile() && fileEntry.getName().endsWith(".sql")) {
                    //read file line by line
                    readFile(fileEntry);
                }
            }
        }
    }


    public void readFile(File fileEntry){
        try (Stream<String> stream = Files.lines(Paths.get(fileEntry.getPath()))) {
            stream.forEach(result::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getLines(){
        return result;
    }
}
