package DatabaseManager.SQLUtils;

import DatabaseManager.exceptions.QueryException;

import java.sql.*;

public class SQLExecuter {

    public static void execute(String query) throws QueryException {
        try {
            String URI = "jdbc:postgresql://localhost:5432/DatabaseInfo",
                    login = "postgres",
                    password = "p9nfeebx";
            Connection connection = DriverManager.getConnection(URI,
                    login, password);
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException exception) {
            throw new QueryException(3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
