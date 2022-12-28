package DatabaseManager.entities;

import DatabaseManager.exceptions.QueryInitializationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.persistence.*;
import java.io.Serializable;

import static java.lang.Long.parseLong;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractQueryEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column
    protected String value;


}
