package DatabaseManager.Entities;

import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.exceptions.TableDoesNotExistException;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.persistence.*;

import static java.lang.Long.parseLong;

@Getter
@Setter
@Entity
@Table(name="common_queries")
public class CommonQueryEntity {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column
    private String value;

    public CommonQueryEntity() {}

    public CommonQueryEntity(String jsonString) throws QueryInitializationException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(jsonString);
            if (!json.containsKey("query")) {
                throw new QueryInitializationException(0);
            }
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