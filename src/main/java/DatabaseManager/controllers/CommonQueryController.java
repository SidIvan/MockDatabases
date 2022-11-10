package DatabaseManager.controllers;

import DatabaseManager.Entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.services.CommonQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/single-query")
public class CommonQueryController {

    @Autowired
    CommonQueryService commonQueryService;

    @PostMapping("/add-new-query")
    ResponseEntity<Long> createQuery(@RequestBody String jsonString) {
        try {
            Long id = commonQueryService.createQuery(jsonString);
            return new ResponseEntity<Long>(id, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/modify-single-query")
    ResponseEntity<String> changeQuery(@RequestBody String jsonString) {
        try {
            commonQueryService.changeQuery(jsonString);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<String> (ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    ResponseEntity<String> deleteQuery(@PathVariable(name = "id") String id) {
        try {
            commonQueryService.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-single-query-by-id/{id}")
    ResponseEntity<CommonQueryEntity> getById(@PathVariable(name = "id") String id) {
        try {
            CommonQueryEntity query = commonQueryService.getById(id);
            return new ResponseEntity<>(query, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get-all-single-queries")
    ResponseEntity<List<CommonQueryEntity>> getById() {
        try {
            List<CommonQueryEntity> queries = commonQueryService.getAll();
            return new ResponseEntity<>(queries, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/execute-single-query-by-id/{id}")
    ResponseEntity<String> executeQuery(@PathVariable(name = "id") String id) {
        try {
            commonQueryService.executeQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
