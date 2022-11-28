package DatabaseManager.repositories;

import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.sqlConstructors.TableSQLConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.*;

@Repository
public class OldTablesRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static final String URI = "jdbc:postgresql://localhost:5432/DatabaseInfo",
                         login = "postgres",
                         password = "p9nfeebx";

    public void sendCreate(String sqlQuery) throws TableInitializationException {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction trans = entityManager.getTransaction();
            trans.begin();
            entityManager.createNativeQuery(sqlQuery).executeUpdate();
            trans.commit();

        } catch (javax.persistence.PersistenceException ex) {
            throw new TableInitializationException(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isTableExists(String tableName) {
        try {
            Connection connection = DriverManager.getConnection(URI, login, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(TableSQLConstructor.constructCheckExistence(tableName));
            resultSet.next();
            return resultSet.getBoolean("exists");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public JSONObject sendGetByName(String sqlQuery, String tableName) {
        try {
            Connection connection = DriverManager.getConnection(URI,
                    login, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            JSONObject result = new JSONObject();
            JSONArray columnInfos = new JSONArray();
            while (resultSet.next()) {
                JSONObject columnInfo = new JSONObject();
                columnInfo.put(resultSet.getString("column_name").toUpperCase(),
                        resultSet.getString("data_type").toUpperCase());
                columnInfos.add(columnInfo);
            }
            if (columnInfos.size() == 0) {
                if (!isTableExists(tableName)) {
                    return new JSONObject();
                }
            }
            ResultSet primaryKey = statement.executeQuery(TableSQLConstructor.constructGetPrimaryKeyName(tableName));
            primaryKey.next();
            result.put("primaryKey", primaryKey.getString("column_name"));
            result.put("columnInfos", columnInfos);
            result.put("columnsAmount", columnInfos.size());
            result.put("tableName", tableName);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONObject();
    }

    public void sendDelete(String sqlQuery, String name) throws TableDoesNotExistException {
        try {
            String URI = "jdbc:postgresql://localhost:5432/DatabaseInfo",
                    login = "postgres",
                    password = "p9nfeebx";
            Connection connection = DriverManager.getConnection(URI,
                    login, password);
            Statement statement = connection.createStatement();
            ResultSet isTableExists = statement.executeQuery(TableSQLConstructor.constructCheckExistence(name));
            isTableExists.next();
            if (!isTableExists.getBoolean("exists")) {
                throw new TableDoesNotExistException();
            }
            statement.executeUpdate(sqlQuery);
        } catch (TableDoesNotExistException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
