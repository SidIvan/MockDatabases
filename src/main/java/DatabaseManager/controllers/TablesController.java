package DatabaseManager.controllers;

import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.services.TablesService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-table-by-name/{name}")
    public ResponseEntity<JSONObject> getTableByName(@PathVariable(value="name") String name) {
        try {
            JSONObject json = tablesService.getTableByName(name);
            if (json.isEmpty()) {
                ResponseEntity<JSONObject> response = new ResponseEntity<>(HttpStatus.OK);
                return response;
            }
            ResponseEntity<JSONObject> response = new ResponseEntity<>(json, HttpStatus.OK);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("drop-table-by-name/{name}")
    public ResponseEntity<String> deleteTableByName(@PathVariable(value="name") String tableName) {
        try {
            tablesService.deleteTableByName(tableName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TableDoesNotExistException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
