package DatabaseManager.services;


import DatabaseManager.SQLUtils.SQLExecuter;
import DatabaseManager.entities.AbstractQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.QueryRepositoryInterface;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class AbstractQueryService<E extends AbstractQueryEntity,
                                            R extends QueryRepositoryInterface<E>> {

    protected final R tableRepository;


    public long createQuery(E queryEntity) throws QueryInitializationException {
        try {
            return tableRepository.saveAndFlush(queryEntity).getId();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void changeQuery(E queryEntity) throws QueryInitializationException {
        try {
            long changeNum = tableRepository.putValue(queryEntity.getId(), queryEntity.getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteQuery(long id) throws QueryInitializationException {
        try {
            int numDeletes = tableRepository.deleteQuery(id);
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

    public E getById(long id) throws QueryInitializationException {
        try {
            Optional<E> query = tableRepository.findById(id);
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
        throw new RuntimeException();
    }

    public List<E> getAll() {
        try {
            List<E> queries = tableRepository.findAll();
            return queries;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void executeQuery(long id) throws QueryInitializationException {
        try {
            Optional<E> query = tableRepository.findById(id);
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
