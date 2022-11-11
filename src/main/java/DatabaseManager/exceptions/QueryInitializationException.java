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
            case 0 -> "Query info have not given";
            case 1 -> "Wrong id format";
            case 2 -> "Query does not exist";
            case 3 -> "Wrong query";
            case 4 -> "Table does not exist";
            case 5 -> "Table info does not given";
            default -> "Unknown exception";
        };
        return res;
    }

}
