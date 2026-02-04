import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class Task {
    private int id;
    private String description;
    private TaskStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    Task(String description) {
        this.id = getLastId();
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    private int getLastId() {
        return 0;
    }


    public String toJsonObjectString() {
        return "{"
                + "\"id\":" + id + ","
                + "\"description\":" + "\"" +description + "\","
                + "\"status\":" + "\"" + status + "\","
                + "\"createdAt\":" + "\"" + createdAt.toString() + "\","
                + "\"updatedAt\":" + "\"" + updatedAt.toString() + "\""
                + "}";
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
