package DatabaseManager.repositories;

import DatabaseManager.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface TableEntityRepository extends JpaRepository<TableEntity, Integer> {

    List<TableEntity> findByTableName(String title);

    @Query(value = "SELECT * FROM table_entities WHERE table_name = :tableName",
        nativeQuery = true)
    List<Optional<TableEntity>> getByTableName(@Param("tableName") String TableName);

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM table_entities WHERE table_name = :name", nativeQuery = true)
//    int deleteByName(String name);
}
