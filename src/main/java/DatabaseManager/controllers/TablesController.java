package DatabaseManager.controllers;

import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.services.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/table")
public class TablesController {

    @Autowired
    private TablesService tablesService;

    @PostMapping("/create-table")
    public ResponseEntity<String> createTable(@RequestBody String tableInfo) {

        try {
            tablesService.createTable(tableInfo);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (TableInitializationException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
