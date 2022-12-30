package DatabaseManager.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TableDTO {

    private long id;

    private String tableName;

    private Set<TableQueryDTO> tableQueryEntities;
}
