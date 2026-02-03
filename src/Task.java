import java.time.LocalDate;

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

    public String toJsonObjectString() {
        return "{" + "\n" +
                "\"id\":" + id + "," +"\n" +
                "\"description\":" + "\"" +description + "\"," + "\n" +
                "\"status\":" + "\"" +status + "\"," + "\n" +
                "\"createdAt\":" + "\"" + createdAt.toString() + "\","+ "\n" +
                "\"updatedAt\":" + "\"" + updatedAt.toString() + "\"" + "\n" +
                '}';
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
