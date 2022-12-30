package DatabaseManager.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
