import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class TaskCli {
    final private String path = "C:\\taskcli";
    final private String jsonName = "taskCliDba.json";

    TaskCli() {
        createDirectory();
        createJsonFile();
    }

    public void createJsonFile() {
        System.out.print("Json file check... ");
        try {
            File jsonFile = new File(getFullFilePath());
            if(!jsonFile.createNewFile()) {
                checkFileExistence(jsonFile);
            } else {
                System.out.println("Created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFullFilePath() {
        return path + "\\" + jsonName;
    }

    private void checkFileExistence(File fileObj) throws IOException {
        if(fileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("Não foi possível criar o arquivo");
        }
    }

    private void createDirectory() {
        System.out.print("Directory check... ");
        try {
            File pathDirectory = new File(path);
            if(!pathDirectory.mkdir()) {
                checkDirectoryExistance(pathDirectory);
            } else {
                System.out.println("Created!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDirectoryExistance(File directoryFileObj) throws IOException {
        if(directoryFileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("Não foi possível criar o dirétório");
        }
    }
}
