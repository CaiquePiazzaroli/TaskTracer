import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskFileManager {
    private final String filename;
    private final String directory;

    TaskFileManager(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
    }

    public int getLastId() {
        return getTasksList().stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0);
    }

    public void saveTask(Task newTask) {
        List<Task> tasks = getTasksList();
        tasks.add(newTask);
        saveTaskList(tasks);
        System.out.println("A task foi salva com sucesso [ID: " + newTask.getId() + "]");
    }

    public void updateTask(int id, String description) {
        List<Task> tasks = getTasksList();
        if(!tasks.isEmpty()) {
            boolean finded = false;
            for(Task task : tasks) {
                if(task.getId() == id) {
                    task.setDescription(description);
                    finded = true;
                    break;
                }
            }

            if(!finded) {
                System.out.println("O ID não foi encontrado na base de dados");
                return;
            }

            if(!saveTaskList(tasks)) {
                System.out.println("Nao foi possivel atualizar a task");
                return;
            }

            System.out.println("Task atualizada com sucesso!");

        } else {
            System.out.println("ID não encontrado, base de dados Vazia");
        }
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

    private String resolveDatabasePath() {
        return Paths.get(directory, filename).toString();
    }

    private void checkDirectoryExistence(File directoryFileObj) throws IOException {
        if(directoryFileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("WARNING: The directory could not be created.");
        }
    }

    private void checkFileExistence(File fileObj) throws IOException {
        if(fileObj.exists()) {
            System.out.println(" OK");
        } else {
            throw new IOException("WARNING: The file could not be created.");
        }
    }

    public List<Task> getTasksList() {
        List<Task> taskAsTasks = new ArrayList<>();
        if(!(getStringTasksList().length == 0)) {
            for(String task : getStringTasksList()) {
                taskAsTasks.add(Task.fromJson(task));
            }
        }
        return taskAsTasks;
    }

    private String[] getStringTasksList() {
        String content = readDatabaseFile();

        if (content == null || content.isBlank()) {
            return new String[0];
        }

        return content.trim()
                .replaceAll("^\\[|]$", "")
                .split("(?<=}),\\s*(?=\\{)");
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

    public boolean saveTaskList(List<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter(resolveDatabasePath());
            fileWriter.write("[");
            for (int i = 0; i < tasks.size(); i++) {
                boolean isLastTask = (i == tasks.size() - 1);
                //System.out.println("Salvando... " + tasks.get(i));
                if(isLastTask) {
                    fileWriter.write(tasks.get(i).toJson());
                } else {
                    fileWriter.write(tasks.get(i).toJson() + ",");
                }
            }
            fileWriter.write("]");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar salver: " + e);
            return false;
        }
    }
}
