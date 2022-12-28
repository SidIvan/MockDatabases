package DatabaseManager.repositories;

import DatabaseManager.entities.TableQueryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TableQueryRepository extends QueryRepositoryInterface<TableQueryEntity> {

    String databaseTable = "table_queries";

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM " + databaseTable + " WHERE id = :id",
            nativeQuery = true)
    int deleteQuery(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE " + databaseTable + " SET value = :value WHERE id = :id",
            nativeQuery = true)
    int putValue(long id, String value);
}
