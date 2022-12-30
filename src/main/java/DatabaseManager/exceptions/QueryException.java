package DatabaseManager.exceptions;

public class QueryException extends MyException {

    private String exParam;

    public QueryException(int id) {
        this.id = id;
    }

    public QueryException(int id, String exParam) {this.id = id; this.exParam = exParam;};

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        String res = switch (id) {
            case 10 -> "Query id is required but not given";
            case 11 -> "Query with id = " + exParam + " does not exist";
            case 20 -> "Query value not given";
            case 30 -> "Table's name that owns query not given";
            case 31 -> "Table \"" + exParam + "\" does not exist";
            case 40 -> "Query did not change";
            case 50 -> "Query did not delete";
            default -> "Query initialization exception id: " + id;
        };
        return res;
    }

}
