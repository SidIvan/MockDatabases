package DatabaseManager.controllers;

import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.services.CommonQueryService;
import DatabaseManager.services.TableQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table-query")
public class TableQueryController {

    @Autowired
    TableQueryService tableQueryService;

    @PostMapping("/add-new-query-to-table")
    ResponseEntity<Long> createQuery(@RequestBody String jsonString) {
        try {
            Long id = tableQueryService.createQuery(jsonString);
            return new ResponseEntity<Long>(id, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/modify-query-in-table")
    ResponseEntity<String> changeQuery(@RequestBody String jsonString) {
        try {
            tableQueryService.changeQuery(jsonString);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<String> (ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
