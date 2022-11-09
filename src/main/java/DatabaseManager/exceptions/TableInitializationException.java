package DatabaseManager.exceptions;

public class TableInitializationException extends Exception {
    private int id;
    private String tableName;

    public TableInitializationException(int id) {
        this.id = id;
    }

    public TableInitializationException(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        String message = switch (id) {
            case 0 -> "ERROR: Table already exists";
            case 1 -> "ERROR: Table name not passed";
            case 2 -> "ERROR: Empty set of columns";
            case 3 -> "ERROR: Primary key info does not exist";
            case 4 -> "ERROR: Column title does not exist";
            case 5 -> "ERROR: Column type does not exist";
            default -> "ERROR: Unknown exception";
        };
        return message;
    }
}
