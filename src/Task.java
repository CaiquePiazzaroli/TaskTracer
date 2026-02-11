import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private String description;
    private TaskStatus status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Task(String id, String description, String taskStatus, String createdAt, String updatedAt) {
        this.id = Integer.parseInt(id);
        this.description = description;
        this.status = TaskStatus.of(taskStatus);
        this.createdAt = LocalDate.parse(createdAt);
        this.updatedAt = LocalDate.parse(updatedAt);
    }

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

    public int getId() {
        return id;
    }

    public static Task fromJson(String taskString) {
        List<String> attributesValuesList = new ArrayList<>();

        String[] taskAttributes = taskString
                .replace("{", "")
                .replace("}","")
                .split(",\"");

        for(String attribute: taskAttributes) {
            if(!attribute.contains(":")) {
                throw new IllegalArgumentException("Json da task Invalido: " + taskString);
            } else {
                attribute = attribute
                        .replace("\"", "")
                        .replace(",", "")
                        .split(":")[1].trim();

                attributesValuesList.add(attribute);
            }
        }

        return new Task(attributesValuesList.get(0),
                attributesValuesList.get(1),
                attributesValuesList.get(2),
                attributesValuesList.get(3),
                attributesValuesList.get(4));
    }

    public String toJson() {
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
