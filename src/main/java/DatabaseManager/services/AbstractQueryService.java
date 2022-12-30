package DatabaseManager.services;


import DatabaseManager.SQLUtils.SQLExecuter;
import DatabaseManager.entities.AbstractQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.QueryException;
import DatabaseManager.repositories.QueryRepositoryInterface;
import DatabaseManager.repositories.TableEntityRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class AbstractQueryService<E extends AbstractQueryEntity,
                                            R extends QueryRepositoryInterface<E>> {

    protected final R tableRepository;

    protected final TableEntityRepository tableEntityRepository;


    public long createQuery(E queryEntity) throws MyException {
        try {
            return tableRepository.saveAndFlush(queryEntity).getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int changeQuery(E queryEntity) throws MyException {
        try {
            int changeNum = tableRepository.putValue(queryEntity.getId(), queryEntity.getValue());
            if (changeNum == 0) {
                throw new QueryException(40);
            }
            return changeNum;
        } catch (MyException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int deleteQuery(long id) throws QueryException {
        try {
            int numDeletes = tableRepository.deleteQuery(id);
            if (numDeletes == 0) {
                throw new QueryException(50);
            }
            return numDeletes;
        } catch (QueryException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public E findById(long id) throws QueryException {
        try {
            Optional<E> query = tableRepository.findById(id);
            if (query.isEmpty()) {
                throw new QueryException(11, id + "");
            }
            return query.get();
        } catch (QueryException ex) {
            throw ex;
        } catch (Exception ex) {
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

    public void executeQuery(long id) throws QueryException {
        try {
            Optional<E> query = tableRepository.findById(id);
            if (query.isEmpty()) {
                throw new QueryException(2);
            }
            SQLExecuter.execute(query.get().getValue());
        } catch (NumberFormatException ex) {
            throw new QueryException(1);
        } catch (QueryException ex) {
            throw ex;
        }
    }



}
