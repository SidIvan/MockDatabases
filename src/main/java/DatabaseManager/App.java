package DatabaseManager;

import DatabaseManager.sqlConstructors.TableSQLConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.Integer.parseInt;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.run(args);
    }
}
