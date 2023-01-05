package DatabaseManager.controllers;

import DatabaseManager.exceptions.TableDoesNotExistException;
import DatabaseManager.exceptions.TableInitializationException;
import DatabaseManager.services.TableService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/table")
public class TableController {

    private final TableService tableService;

    @PostMapping("/create-table")
    public ResponseEntity<String> createTable(@RequestBody String tableInfo) {
        try {
            tableService.createTable(tableInfo);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } catch (TableInitializationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Error message", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .headers(responseHeaders)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-table-by-name/{name}")
    public ResponseEntity<JSONObject> getTableByName(@PathVariable(value="name") String name) {
        try {
            JSONObject json = tableService.getTableByName(name);
            if (json.isEmpty()) {
                ResponseEntity<JSONObject> response = new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("drop-table-by-name/{name}")
    public ResponseEntity<String> deleteTableByName(@PathVariable(value="name") String tableName) {
        try {
            tableService.deleteTableByName(tableName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TableDoesNotExistException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Error message", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .headers(responseHeaders)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
