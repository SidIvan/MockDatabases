package DatabaseManager.repositories;

import DatabaseManager.Entities.CommonQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommonQueryRepository extends JpaRepository<CommonQueryEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM common_queries WHERE id = :id",
            nativeQuery = true)
    int deleteQuery(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE common_queries SET value = :value WHERE id = :id",
            nativeQuery = true)
    int putValue(long id, String value);

}
