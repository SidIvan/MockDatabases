package DatabaseManager.services;

import DatabaseManager.Entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.CommonQueryRepository;
import DatabaseManager.repositories.SQLExecuter;
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
public class CommonQueryService {

    @Autowired
    CommonQueryRepository commonQueryRepository;

    public long createQuery(String jsonString) throws QueryInitializationException {
        try {
            CommonQueryEntity query = new CommonQueryEntity(jsonString);
            return commonQueryRepository.saveAndFlush(query).getId();
        } catch (QueryInitializationException ex) {
            throw ex;
        }
    }

    public void changeQuery(String jsonString) throws QueryInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(jsonString);
            if (!json.containsKey("queryId") || !json.containsKey("query")) {
                throw new QueryInitializationException(0);
            }
            long id = parseLong(json.get("queryId").toString());
            long changeNum = commonQueryRepository.putValue(id, json.get("query").toString());
            if (changeNum == 0) {
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
            int numDeletes = commonQueryRepository.deleteQuery(parseInt(id));
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

    public CommonQueryEntity getById(String id) throws QueryInitializationException {
        try {
            Optional<CommonQueryEntity> query = commonQueryRepository.findById(parseLong(id));
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
        return new CommonQueryEntity();
    }

    public List<CommonQueryEntity> getAll() {
        try {
            List<CommonQueryEntity> queries = commonQueryRepository.findAll();
            return queries;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void executeQuery(String id) throws QueryInitializationException {
        try {
            long queryId = parseLong(id);
            Optional<CommonQueryEntity> query = commonQueryRepository.findById(parseLong(id));
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
