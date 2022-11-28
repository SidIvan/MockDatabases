package DatabaseManager.exceptions;

public class TableInitializationException extends Exception {
    private final int id;

    public TableInitializationException() {
        id = -1;
    }

    public TableInitializationException(int id) {
        this.id = id;
    }

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        return switch (id) {
            case 0 -> "ERROR: Table already exists";
            case 1 -> "ERROR: Table name not passed";
            case 2 -> "ERROR: Empty set of columns";
            case 3 -> "ERROR: Primary key info does not exist";
            case 4 -> "ERROR: Column title does not exist";
            case 5 -> "ERROR: Column type does not exist";
            case 6 -> "ERROR: Wrong column info";
            case 7 -> "ERROR: Wrong JSON format";
            default -> "ERROR: Unknown exception";
        };
    }

    public int getId() {
        return id;
    }
}
