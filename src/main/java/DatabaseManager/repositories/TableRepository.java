package DatabaseManager.repositories;

import DatabaseManager.SQLUtils.DatabaseProperties;
import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.SQLUtils.TableSQLConstructor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class TableRepository {

    private final DatabaseProperties databaseProperties;
    @Deprecated
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Deprecated
    public void entityManagerSendCreate(String sqlQuery) throws TableInitializationException {
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

    public void sendCreate(String sqlQuery) throws TableInitializationException {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(), databaseProperties.getPassword());
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            int errId = -1;
            if (ex.getSQLState().equals("42P07")) {
                errId = 0;
            } else if (ex.getSQLState().equals("42704")) {
                errId = 6;
            }
            throw new TableInitializationException(errId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public boolean isTableExists(String tableName) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(), databaseProperties.getPassword());
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
            Connection connection = DriverManager.getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(), databaseProperties.getPassword());
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
            Connection connection = DriverManager.getConnection(databaseProperties.getUrl(),
                    databaseProperties.getUsername(), databaseProperties.getPassword());
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
