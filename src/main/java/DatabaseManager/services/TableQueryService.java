package DatabaseManager.services;

import DatabaseManager.Entities.TableEntity;
import DatabaseManager.Entities.TableQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.SQLExecuter;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.repositories.TableQueryRepository;
import DatabaseManager.repositories.OldTablesRepository;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Service
public class TableQueryService {

    @Autowired
    TableQueryRepository tableQueryRepository;

    @Autowired
    TableEntityRepository tableEntityRepository;

    public long createQuery(String jsonString) throws QueryInitializationException {
        try {
            TableQueryEntity query = new TableQueryEntity(jsonString);
            System.out.println(query.getTableName());
            Optional<TableEntity> tableEntity = tableEntityRepository.getByTableName(query.getTableName()).get(0);
            if (tableEntity.isEmpty()) {
                throw new QueryInitializationException(4);
            }
            query.setTableEntity(tableEntity.get());
            return tableQueryRepository.saveAndFlush(query).getId();
        } catch (QueryInitializationException ex) {
            throw ex;
        }
    }

    public void changeQuery(String jsonString) throws QueryInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(jsonString);
            if (!json.containsKey("queryId") || !json.containsKey("query") || !json.containsKey("tableName")) {
                throw new QueryInitializationException(0);
            }
            long id = parseLong(json.get("queryId").toString());
            long changeNum = tableQueryRepository.putValue(id, json.get("query").toString(), json.get("tableName").toString());
            if (changeNum == 0) {
                if (!OldTablesRepository.isTableExists(json.get("tableName").toString())) {
                    throw new QueryInitializationException(4);
                }
                throw new QueryInitializationException(2);
            }
        } catch (QueryInitializationException ex) {
            throw ex;
        } catch (NumberFormatException ex) {
            throw new QueryInitializationException(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteQuery(String id) throws QueryInitializationException {
        try {
            int numDeletes = tableQueryRepository.deleteQuery(parseInt(id));
            if (numDeletes == 0) {
                throw new QueryInitializationException(2);
            }
        } catch (NumberFormatException ex) {
            throw new QueryInitializationException(1);
        } catch (QueryInitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public TableQueryEntity getById(String id) throws QueryInitializationException {
        try {
            Optional<TableQueryEntity> query = tableQueryRepository.findById(parseLong(id));
            if (query.isEmpty()) {
                throw new QueryInitializationException(2);
            }
            return query.get();
        } catch (NumberFormatException ex) {
            throw new QueryInitializationException(1);
        } catch (QueryInitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            System.out.println("A");
            ex.printStackTrace();
        }
        return new TableQueryEntity();
    }

    public List<TableQueryEntity> getAll() {
        try {
            List<TableQueryEntity> queries = tableQueryRepository.findAll();
            return queries;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void executeQuery(String id) throws QueryInitializationException {
        try {
            long queryId = parseLong(id);
            Optional<TableQueryEntity> query = tableQueryRepository.findById(parseLong(id));
            if (query.isEmpty()) {
                throw new QueryInitializationException(2);
            }
            SQLExecuter.execute(query.get().getValue());
        } catch (NumberFormatException ex) {
            throw new QueryInitializationException(1);
        } catch (QueryInitializationException ex) {
            throw ex;
        }
    }
}
