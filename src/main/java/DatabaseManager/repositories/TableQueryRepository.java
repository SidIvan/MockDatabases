package DatabaseManager.repositories;

import DatabaseManager.Entities.TableQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TableQueryRepository extends JpaRepository<TableQueryEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM table_queries WHERE id = :id",
            nativeQuery = true)
    int deleteQuery(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE table_queries SET value = :value WHERE id = :id AND table_name = :tableName",
            nativeQuery = true)
    int putValue(long id, String value, String tableName);
}
