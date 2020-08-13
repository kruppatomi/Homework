import java.io.File;

public class Filereader {

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }
            else {
                if(fileEntry.isFile() && fileEntry.getName().endsWith(".sql"))
                    System.out.println(fileEntry.getName());
                else{
                    continue;
                }
            }
        }
    }
}
