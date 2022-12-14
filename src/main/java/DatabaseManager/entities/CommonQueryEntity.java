package DatabaseManager.entities;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.exceptions.QueryException;
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
@Table(name="common_queries")
public class CommonQueryEntity extends AbstractQueryEntity {

    public CommonQueryEntity(CommonQueryDTO dto) {
        super.setId(dto.getId());
        super.setValue(dto.getValue());
    }

    @Deprecated
    public CommonQueryEntity(String jsonString) throws QueryException {
        try {
            JSONObject json = (JSONObject) JSONValue.parseWithException(jsonString);
            if (!json.containsKey("query")) {
                throw new QueryException(0);
            }
            value = json.get("query").toString();
            if (json.containsKey("id")) {
                id = parseLong(json.get("id").toString());
            }
        } catch (QueryException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
