package DatabaseManager.repositories;

import DatabaseManager.Entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface TableEntityRepository extends JpaRepository<TableEntity, Integer> {

    @Query(value = "SELECT * FROM table_entities WHERE table_name = :tableName",
        nativeQuery = true)
    List<Optional<TableEntity>> getByTableName(@Param("tableName") String TableName);
}
