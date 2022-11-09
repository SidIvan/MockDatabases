package DatabaseManager.services;

import DatabaseManager.Entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.CommonQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
