package DatabaseManager.services;

import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.repositories.TablesRepository;
import DatabaseManager.sqlConstructors.TableSQLConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

@Service
public class TablesService {

    @Autowired
    TablesRepository tablesRepository;
    public void createTable(String tableInfo) throws TableInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(tableInfo);
            String sqlQuery = TableSQLConstructor.constructCreate(json);
            tablesRepository.sendCreate(sqlQuery);
        } catch (TableInitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject getTableByName(String name) {
        try {
            String sqlQuery = TableSQLConstructor.constructGetByName(name);
            JSONObject json = tablesRepository.sendGetByName(sqlQuery, name);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONObject();
    }

    public void deleteTableByName(String name) throws TableDoesNotExistException {
        try {
            String sqlQuery = TableSQLConstructor.constructDelete(name);
            tablesRepository.sendDelete(sqlQuery, name);
        } catch (TableDoesNotExistException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
