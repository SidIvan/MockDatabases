package DatabaseManager.services;

import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.repositories.CommonQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class CommonQueryService extends AbstractQueryService<CommonQueryEntity, CommonQueryRepository> {


    public CommonQueryService(CommonQueryRepository repository) {
        super(repository);
    }

}