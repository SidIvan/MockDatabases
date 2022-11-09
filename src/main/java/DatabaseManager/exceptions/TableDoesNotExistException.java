package DatabaseManager.exceptions;

public class TableDoesNotExistException extends Exception {

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        return "Table does not exist";
    }
}
