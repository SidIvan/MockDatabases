package DatabaseManager.services;

import DatabaseManager.entities.TableEntity;
import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.repositories.TableRepository;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.SQLUtils.TableSQLConstructor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TableService {


    private final TableRepository tableRepository;

    @Autowired
    TableEntityRepository tableEntityRepository;

    public void createTable(String tableInfo) throws TableInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(tableInfo);
            String sqlQuery = TableSQLConstructor.constructCreate(json);
            tableRepository.sendCreate(sqlQuery);
            TableEntity tableEntity = new TableEntity(json.get("tableName").toString());
            tableEntityRepository.saveAndFlush(tableEntity);
        } catch (TableInitializationException ex) {
            throw ex;
        } catch (ParseException ex) {
            throw new TableInitializationException(7);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject getTableByName(String name) {
        try {
            String sqlQuery = TableSQLConstructor.constructGetByName(name);
            JSONObject json = tableRepository.sendGetByName(sqlQuery, name);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONObject();
    }

    public void deleteTableByName(String name) throws TableDoesNotExistException {
        try {
            String sqlQuery = TableSQLConstructor.constructDelete(name);
            TableEntity tableEntity = tableEntityRepository.findByTableName(name).get(0);
            tableEntityRepository.delete(tableEntity);
            tableRepository.sendDelete(sqlQuery, name);
        } catch (IndexOutOfBoundsException ex) {
            throw new TableDoesNotExistException();
        } catch (TableDoesNotExistException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
