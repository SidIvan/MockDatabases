package DatabaseManager.services;


import DatabaseManager.entities.AbstractQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.QueryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class AbstractQueryService<E extends AbstractQueryEntity,
        R extends QueryRepositoryInterface<E>> {

//    protected final R tableRepository;
//
//    @Autowired
//    public AbstractQueryService(R repository) {
//        this.tableRepository = repository;
//    }
//
//    public long createQuery(E jsonString) throws QueryInitializationException {
//        try {
//            E query = new E(jsonString);
//            return tableRepository.saveAndFlush(query).getId();
//        } catch (QueryInitializationException ex) {
//            throw ex;
//        }
//    }

}
