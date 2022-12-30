package DatabaseManager.services;

import DatabaseManager.entities.TableEntity;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.QueryException;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.repositories.TableQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Service
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
//
//    public TableQueryEntity getById(long id) throws QueryInitializationException {
//        try {
//            Optional<TableQueryEntity> query = tableQueryRepository.findById(id);
//            if (query.isEmpty()) {
//                throw new QueryInitializationException(2);
//            }
//            return query.get();
//        } catch (NumberFormatException ex) {
//            throw new QueryInitializationException(1);
//        } catch (QueryInitializationException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            System.out.println("A");
//            ex.printStackTrace();
//        }
//        return new TableQueryEntity();
//    }
//
//    public List<TableQueryEntity> getAll() {
//        try {
//            List<TableQueryEntity> queries = tableQueryRepository.findAll();
//            return queries;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public void executeQuery(String id) throws QueryInitializationException {
//        try {
//            long queryId = parseLong(id);
//            Optional<TableQueryEntity> query = tableQueryRepository.findById(parseLong(id));
//            if (query.isEmpty()) {
//                throw new QueryInitializationException(2);
//            }
//            SQLExecuter.execute(query.get().getValue());
//        } catch (NumberFormatException ex) {
//            throw new QueryInitializationException(1);
//        } catch (QueryInitializationException ex) {
//            throw ex;
//        }
//    }
}
