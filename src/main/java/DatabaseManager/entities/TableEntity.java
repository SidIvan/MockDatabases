package DatabaseManager.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "table_entities")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String tableName;

    @OneToMany(mappedBy = "tableEntity",
                cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<TableQueryEntity> tableQueryEntities;

    public TableEntity(String tableName) {
        this.tableName = tableName;
    }

}
