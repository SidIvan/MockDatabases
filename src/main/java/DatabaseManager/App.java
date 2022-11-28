package DatabaseManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.Integer.parseInt;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
