package DatabaseManager.services;

import DatabaseManager.entities.TableEntity;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.QueryException;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.repositories.TableQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableQueryService extends AbstractQueryService<TableQueryEntity, TableQueryRepository> {

    public TableQueryService(TableQueryRepository repository, TableEntityRepository repository2) {
        super(repository, repository2);
    }

    private void setTableEntity(TableQueryEntity queryEntity) throws MyException {
        List<TableEntity> possibleTableEntity = tableEntityRepository.findByTableName(queryEntity.getTableName());
        if (possibleTableEntity.size() == 0) {
            throw new QueryException(31, queryEntity.getTableName());
        }
        queryEntity.setTableEntity(possibleTableEntity.get(0));
    }

    public long createQuery(TableQueryEntity queryEntity) throws MyException {
        setTableEntity(queryEntity);
        return super.createQuery(queryEntity);
    }

    public int changeQuery(TableQueryEntity queryEntity) throws MyException {
        setTableEntity(queryEntity);
        return super.changeQuery(queryEntity);
    }

}
