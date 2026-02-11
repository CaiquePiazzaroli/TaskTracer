public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String description;
    TaskStatus(String todo) {
        this.description = todo;
    }

    public String getDescription() {
        return description;
    }

    public static TaskStatus of(String status) {
        if(!status.equals("TODO") && !status.equals("DONE") && !status.equals("IN_PROGRESS")) {
            System.out.println("Erro, formato inválido: " + status);
        }
        return switch (status) {
            case "TODO" -> TaskStatus.TODO;
            case "DONE" -> TaskStatus.DONE;
            default -> TaskStatus.IN_PROGRESS;
        };
    }
}
