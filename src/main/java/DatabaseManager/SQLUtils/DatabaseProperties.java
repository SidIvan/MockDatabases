package DatabaseManager.SQLUtils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("spring.datasource")
public class DatabaseProperties {

    private String url;

    private String username;

    private String password;

}
