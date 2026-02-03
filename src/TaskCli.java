import java.io.File;
import java.io.IOException;


public class TaskCli {
    final private String path = "C:\\taskcli";
    final private String jsonName = "taskCliDba.json";

    TaskCli() {
        createDirectory();
        createJsonFile();
    }

    static void main(String[] args) {
        doAction(args);
    }

    private static void doAction(String[] args) {
        try {
            String action = args[0];
            switch (action) {
                case "add":
                    addTask(args);
                    break;
                case "update":
                    updateTask(args);
                    break;
                case "delete":
                    deleteTask(args);
                    break;
                case "mark-in-progress":
                    markInProgress(args);
                    break;
                case "mark-done":
                    markDone(args);
                    break;
                case "list":
                    listTasks(args);
                    break;
                default:
                    System.out.println("Option not found, insert a valid option [add, update, delete, mark-in-progress, mark-done or list]");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Insert a valid option [add, update, delete, mark-in-progress, mark-done or list]");
        }
    }

    private static void listTasks(String[] args) {
        try {
            String status = args[1];
            if(checkValidStatusArgument(status)) {
                listStatusTasks(status);
            } else {
                throw new IllegalArgumentException("The type of status task is invalid");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Insert a valid status [todo, done or in-progress]");
        } catch (IndexOutOfBoundsException e) {
            listAllTasks();
        }
    }

    private static boolean checkValidStatusArgument(String taskStatus) {
        boolean haveTodoArgument = taskStatus.equals(TaskStatus.TODO.getDescription());
        boolean haveInProgressArgument = taskStatus.equals(TaskStatus.IN_PROGRESS.getDescription());
        boolean haveDoneArgument = taskStatus.equals(TaskStatus.DONE.getDescription());
        return  haveTodoArgument || haveInProgressArgument || haveDoneArgument;
    }

    private static void listStatusTasks(String status) {
        switch (status) {
            case "done":
                System.out.println("Show all DONE tasks!");
                break;
            case "todo":
                System.out.println("Show all TODO tasks!");
                break;
            case "in-progress":
                System.out.println("Show All IN_PROGRESS tasks!");
                break;
            default:
                System.out.println("Insert a valid status -> (done, todo ou in-progress)");
        }
    }

    private static void listAllTasks() {
        System.out.println("Listing all tasks.....");
    }

    private static void markDone(String[] args) {
        try {
            int idDescription = Integer.parseInt(args[1]);
            System.out.println("ID" + idDescription + " Marked as done!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid argument, please use: TaskCli mark-done [idDescription]");
        } catch (NumberFormatException e) {
            System.out.println("Insert a valid id");
        }
    }

    private static void markInProgress(String[] args) {
        try {
            int idDescription = Integer.parseInt(args[1]);
            System.out.println("ID " + idDescription + " Marked as in-progress");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid argument, please use: TaskCli mark-in-progress [idDescription]");
        } catch (NumberFormatException e) {
            System.out.println("Insert a valid id");
        }
    }

    private static void deleteTask(String[] args) {
        try {
            int idDescription = Integer.parseInt(args[1]);
            System.out.println("ID " + idDescription + " was deleted with success!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid argument, please use: TaskCli delete [idDescription]");
        } catch (NumberFormatException e) {
            System.out.println("Insert a valid id");
        }
    }

    private static void addTask(String[] args) {
        try {
            String taskDecription = args[1];
            System.out.println("Adding a task: " + taskDecription);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid argument, please use: TaskCli add [taskDescription]");
        }
    }

    private static void updateTask(String[] args) {
        try {
            String taskDescription = args[2];
            int idDescription = Integer.parseInt(args[1]);
            System.out.println("Updating a task id: " + idDescription + " description: " + taskDescription);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid argument, please use: TaskCli update [idDescription] [taskDescription]");
        } catch (NumberFormatException e) {
            System.out.println("Insert a valid id");
        }
    }

    private void createJsonFile() {
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
            throw new IOException("WARNING: The file could not be created.");
        }
    }

    private void createDirectory() {
        System.out.print("Directory check... ");
        try {
            File pathDirectory = new File(path);
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
}
