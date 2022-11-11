package DatabaseManager.controllers;

import DatabaseManager.Entities.CommonQueryEntity;
import DatabaseManager.Entities.TableQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.services.CommonQueryService;
import DatabaseManager.services.TableQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete-table-query-by-id/{id}")
    ResponseEntity<String> deleteQuery(@PathVariable(name = "id") String id) {
        try {
            tableQueryService.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-table-query-by-id/{id}")
    ResponseEntity<TableQueryEntity> getById(@PathVariable(name = "id") String id) {
        try {
            TableQueryEntity query = tableQueryService.getById(id);
            return new ResponseEntity<>(query, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-all-table-queries")
    ResponseEntity<List<TableQueryEntity>> getById() {
        try {
            List<TableQueryEntity> queries = tableQueryService.getAll();
            return new ResponseEntity<>(queries, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/execute-table-query-by-id/{id}")
    ResponseEntity<String> executeQuery(@PathVariable(name = "id") String id) {
        try {
            tableQueryService.executeQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
