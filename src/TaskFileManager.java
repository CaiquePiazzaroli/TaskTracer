import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskFileManager {
    private final String filename;
    private final String directory;

    TaskFileManager(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
    }

    private String resolveDatabasePath() {
        return Paths.get(directory, filename).toString();
    }

    public void getLastId() {
        String data = readDatabaseFile();
        System.out.println(data);
    }

    public List<Task> getTasksList() {
        List<Task> taskAsTasks = new ArrayList<>();

        for(String ts : getStringTasksList()) {
           taskAsTasks.add(Task.fromJson(ts));
        }
        return taskAsTasks;
    }

    private String[] getStringTasksList() {
        // (?<=}) --> Antes da virgula tem um } ?
        // \\s* --> Pode ou nao conter espaço e apaga
        // (?=\{) --> Contem um { em seguida ?
        // ^\[|]$ --> apaga [ do inicio (^) da linha e ] final (&)
        return readDatabaseFile()
                .trim()
                .replaceAll("^\\[|]$", "")
                .split("(?<=}),\\s*(?=\\{)");
    }

    public void saveTask(Task newTask) {
        try{
            String newDatabaseString = prepareNewStringDatabase(newTask);
            FileWriter fileWriter = new FileWriter(resolveDatabasePath());
            fileWriter.write(newDatabaseString);
            fileWriter.close();
            System.out.println("A task foi salva com sucesso [ID: " + newTask.getId() + "]");
        } catch (IOException e) {
            System.out.println("Não foi possivel salvar");
        }
    }

    private String prepareNewStringDatabase(Task newTask) {
        String objTaskString = newTask.toJson();
        String databaseString = readDatabaseFile();
        if(databaseString.isEmpty()) {
            return "[" + objTaskString + "]";
        } else {
            return removeClosingSquareBracket(databaseString) + ",\n" + objTaskString + "]";
        }
    }

    public String readDatabaseFile() {
        try {
            Path path = Paths.get(resolveDatabasePath());
            if(!Files.exists(path)) {
                return "";
            }
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler base de dados", e);
        }
    }

    private String removeClosingSquareBracket(String json) {
        int lastBracketIndex = json.lastIndexOf("]");
        if (lastBracketIndex != -1) {
            return json.substring(0, lastBracketIndex);
        }
        return json;
    }

    public void createDirectory() {
        System.out.print("Directory check... ");
        try {
            File pathDirectory = new File(directory);
            if(!pathDirectory.mkdir()) {
                checkDirectoryExistence(pathDirectory);
            } else {
                System.out.println("Created!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkDirectoryExistence(File directoryFileObj) throws IOException {
        if(directoryFileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("WARNING: The directory could not be created.");
        }
    }

    public void createJsonFile() {
        System.out.print("Json file check... ");
        try {
            File jsonFile = new File(resolveDatabasePath());
            if(!jsonFile.createNewFile()) {
                checkFileExistence(jsonFile);
            } else {
                System.out.println("Created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFileExistence(File fileObj) throws IOException {
        if(fileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("WARNING: The file could not be created.");
        }
    }
}
