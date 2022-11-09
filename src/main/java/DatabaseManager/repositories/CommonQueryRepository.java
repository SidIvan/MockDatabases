package DatabaseManager.repositories;

import DatabaseManager.Entities.CommonQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonQueryRepository extends JpaRepository<CommonQueryEntity, Long> {

}
