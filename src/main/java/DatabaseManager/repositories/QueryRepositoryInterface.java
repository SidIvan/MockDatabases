package DatabaseManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QueryRepositoryInterface<EntityType> extends JpaRepository<EntityType, Long> {

    int deleteQuery(long id);

    int putValue(long id, String value);
}
