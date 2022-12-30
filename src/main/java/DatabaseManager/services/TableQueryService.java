package DatabaseManager.services;

import DatabaseManager.entities.TableEntity;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.SQLUtils.SQLExecuter;
import DatabaseManager.repositories.TableEntityRepository;
import DatabaseManager.repositories.TableQueryRepository;
import DatabaseManager.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
public class TableQueryService {

    private final TableQueryRepository tableQueryRepository;

    private final TableEntityRepository tableEntityRepository;

    public long createQuery(TableQueryEntity queryEntity) throws QueryInitializationException {
        try {
            List<TableEntity> tableEntities = tableEntityRepository.findByTableName(queryEntity.getTableName());
            if (tableEntities.size() == 0) {
                throw new QueryInitializationException(4);
            }
            queryEntity.setTableEntity(tableEntities.get(0));
            return tableQueryRepository.saveAndFlush(queryEntity).getId();
        } catch (QueryInitializationException ex) {
            throw ex;
        }
    }

    public void changeQuery(TableQueryEntity queryEntity) throws QueryInitializationException {
        try {
            long changeNum = tableQueryRepository.putValue(queryEntity.getId(), queryEntity.getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteQuery(long id) throws QueryInitializationException {
        try {
            int numDeletes = tableQueryRepository.deleteQuery(id);
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


    public TableQueryEntity getById(long id) throws QueryInitializationException {
        try {
            Optional<TableQueryEntity> query = tableQueryRepository.findById(id);
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
