package DatabaseManager.services;

import DatabaseManager.Entities.TableQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.TableQueryRepository;
import DatabaseManager.repositories.TablesRepository;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Long.parseLong;

@Service
public class TableQueryService {

    @Autowired
    TableQueryRepository tableQueryRepository;

    public long createQuery(String jsonString) throws QueryInitializationException {
        try {
            TableQueryEntity query = new TableQueryEntity(jsonString);
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
                if (!TablesRepository.isTableExists(json.get("tableName").toString())) {
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

}
