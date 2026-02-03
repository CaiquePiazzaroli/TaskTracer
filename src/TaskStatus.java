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
}
