import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> propertiesTaskMap = new HashMap<>();

        String[] propertiesTask = taskString
                .replaceAll("^\\{|}$", "")
                .split("(?<=\")?,\\s*(?=\")");

        for (String prop : propertiesTask) {
            String[] keyAndValue = prop.split(":", 2);
            if (keyAndValue.length == 2) {
                String key = keyAndValue[0].trim().replace("\"", "");
                String value = keyAndValue[1].trim().replace("\"", "");

                propertiesTaskMap.put(key, value);
            }
        }
//        System.out.println(propertiesTaskMap.get("id"));
//        System.out.println(propertiesTaskMap.get("description"));
//        System.out.println(propertiesTaskMap.get("status"));
//        System.out.println(propertiesTaskMap.get("createdAt"));
//        System.out.println(propertiesTaskMap.get("updatedAt"));

        return new Task(
                propertiesTaskMap.get("id"),
                propertiesTaskMap.get("description"),
                propertiesTaskMap.get("status"),
                propertiesTaskMap.get("createdAt"),
                propertiesTaskMap.get("updatedAt"));
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
