package DatabaseManager.repositories;

import DatabaseManager.exceptions.TableInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Repository
public class TablesRepository {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void send(String sqlQuery) throws TableInitializationException {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction trans = entityManager.getTransaction();
            trans.begin();
            entityManager.createNativeQuery(sqlQuery).executeUpdate();
            trans.commit();
        } catch (javax.persistence.PersistenceException ex) {
            throw new TableInitializationException(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
