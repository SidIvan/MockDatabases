package DatabaseManager.services;

import DatabaseManager.Entities.TableEntity;
import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.repositories.OldTablesRepository;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.sqlConstructors.TableSQLConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TablesService {

    @Autowired
    OldTablesRepository oldTablesRepository;

    @Autowired
    TableEntityRepository tableEntityRepository;

    public void createTable(String tableInfo) throws TableInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(tableInfo);
            String sqlQuery = TableSQLConstructor.constructCreate(json);
            oldTablesRepository.sendCreate(sqlQuery);
            TableEntity tableEntity = new TableEntity(json.get("tableName").toString());
            tableEntityRepository.saveAndFlush(tableEntity);
        } catch (TableInitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject getTableByName(String name) {
        try {
            String sqlQuery = TableSQLConstructor.constructGetByName(name);
            JSONObject json = oldTablesRepository.sendGetByName(sqlQuery, name);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONObject();
    }

    public void deleteTableByName(String name) throws TableDoesNotExistException {
        try {
            String sqlQuery = TableSQLConstructor.constructDelete(name);
            oldTablesRepository.sendDelete(sqlQuery, name);
        } catch (TableDoesNotExistException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
