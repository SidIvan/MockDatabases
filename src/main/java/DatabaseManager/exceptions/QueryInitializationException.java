package DatabaseManager.exceptions;

public class QueryInitializationException extends Exception {

    private int id;

    public QueryInitializationException(int id) {
        this.id = id;
    }

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        String res = switch (id) {
            case 0 -> "Query info does not exist";
            default -> "Unknown exception";
        };
        return res;
    }

}
