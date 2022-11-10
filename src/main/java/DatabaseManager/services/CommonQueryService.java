package DatabaseManager.services;

import DatabaseManager.Entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.CommonQueryRepository;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
