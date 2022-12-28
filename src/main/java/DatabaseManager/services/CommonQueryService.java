package DatabaseManager.services;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.CommonQueryRepository;
import DatabaseManager.SQLUtils.SQLExecuter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CommonQueryService {

    private final CommonQueryRepository commonQueryRepository;

    public long createQuery(CommonQueryEntity queryEntity) throws QueryInitializationException {
        try {
            return commonQueryRepository.saveAndFlush(queryEntity).getId();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void changeQuery(CommonQueryEntity queryEntity) throws QueryInitializationException {
        try {
            long changeNum = commonQueryRepository.putValue(queryEntity.getId(), queryEntity.getValue());
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

    public CommonQueryEntity getById(long id) throws QueryInitializationException {
        try {
            Optional<CommonQueryEntity> query = commonQueryRepository.findById(id);
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