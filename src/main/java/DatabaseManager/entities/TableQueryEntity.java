package DatabaseManager.entities;

import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.repositories.TableRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.persistence.*;

import static java.lang.Long.parseLong;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="table_queries")
public class TableQueryEntity extends AbstractQueryEntity {

    @Column
    private String tableName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    @JsonBackReference
    private TableEntity tableEntity;




    @Deprecated
    public TableQueryEntity(String jsonString) throws QueryInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(jsonString);
            if (!json.containsKey("query")) {
                throw new QueryInitializationException(0);
            }
            if (!json.containsKey("tableName")) {
                throw new QueryInitializationException(5);
            }
            if (!TableRepository.isTableExists(json.get("tableName").toString())) {
                throw new QueryInitializationException(4);
            }
            tableName = json.get("tableName").toString();
            value = json.get("query").toString();
            if (json.containsKey("id")) {
                id = parseLong(json.get("id").toString());
            }
        } catch (QueryInitializationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
