package DatabaseManager.services;

import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.repositories.CommonQueryRepository;
import DatabaseManager.repositories.TableEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommonQueryService extends AbstractQueryService<CommonQueryEntity, CommonQueryRepository> {


    public CommonQueryService(CommonQueryRepository repository, TableEntityRepository repository2) {
        super(repository, repository2);
    }

}