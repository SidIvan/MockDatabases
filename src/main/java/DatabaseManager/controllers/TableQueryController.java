package DatabaseManager.controllers;

import DatabaseManager.DTO.TableQueryDTO;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.services.TableQueryService;
import DatabaseManager.utils.EntityDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/table-query")
public class TableQueryController {

    private final TableQueryService tableQueryService;

    @PostMapping(value = "/add-new-query-to-table",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> createQuery(@RequestBody TableQueryDTO queryDTO) {
        try {
            Long id = tableQueryService.createQuery(EntityDTOMapper.toEntity(queryDTO));
            return new ResponseEntity<Long>(id, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Error message", ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .headers(responseHeaders)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/modify-query-in-table",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> changeQuery(@RequestBody TableQueryDTO queryDTO) {
        try {
            tableQueryService.changeQuery(EntityDTOMapper.toEntity(queryDTO));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
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

    @DeleteMapping("/delete-table-query-by-id/{id}")
    ResponseEntity<String> deleteQuery(@PathVariable(name = "id") long id) {
        try {
            tableQueryService.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryInitializationException ex) {
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

    @GetMapping("/get-table-query-by-id/{id}")
    ResponseEntity<TableQueryEntity> getById(@PathVariable(name = "id") long id) {
        try {
            TableQueryEntity query = tableQueryService.getById(id);
            return new ResponseEntity<>(query, HttpStatus.OK);
        } catch (QueryInitializationException ex) {
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
