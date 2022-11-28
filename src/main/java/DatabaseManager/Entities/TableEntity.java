package DatabaseManager.Entities;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "table_entities")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String tableName;

    @OneToMany(mappedBy = "tableEntity",
                cascade = CascadeType.ALL)
    private Set<TableQueryEntity> tableQueryEntities;

    public TableEntity() {}

    public TableEntity(String tableName) {
        this.tableName = tableName;
    }

    public TableEntity(JSONObject json) {}
}
