package DatabaseManager;

import DatabaseManager.SQLUtils.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@EnableConfigurationProperties({DatabaseProperties.class})
@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
