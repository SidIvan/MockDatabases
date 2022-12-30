package DatabaseManager.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableQueryDTO {

    private long id;

    private String value;

    private String tableName;

    private TableDTO tableEntity;
}
