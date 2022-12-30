package DatabaseManager.controllers;

import DatabaseManager.entities.AbstractQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.QueryException;
import DatabaseManager.repositories.QueryRepositoryInterface;
import DatabaseManager.services.AbstractQueryService;
import DatabaseManager.utils.GenericEntityDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractQueryController<E extends AbstractQueryEntity, R extends QueryRepositoryInterface<E>,
                            S extends AbstractQueryService<E, R>, D> {

    protected final S service;

    protected final GenericEntityDTOMapper<E, D> mapper;


    @PostMapping(value = "/add-new-query",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> createQuery(@RequestBody D queryDTO) {
        try {
            return new ResponseEntity<Long>(service.createQuery(mapper.toEntity(queryDTO)), HttpStatus.OK);
        } catch (MyException ex) {
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


    @PutMapping(value = "/modify-query",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> changeQuery(@RequestBody D queryDTO) {
        try {
            service.changeQuery(mapper.toEntity(queryDTO));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException ex) {
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

    @DeleteMapping("/delete-query-by-id/{id}")
    ResponseEntity<String> deleteQuery(@PathVariable(name = "id") long id) {
        try {
            service.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryException ex) {
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

    @GetMapping(value = "/get-query-by-id/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<D> getById(@PathVariable(name = "id") long id) {
        try {
            E query = service.findById(id);
            return new ResponseEntity<>(mapper.toDTO(query), HttpStatus.OK);
        } catch (QueryException ex) {
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

    @GetMapping(value = "/get-all-queries",
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<D>> getAll() {
        try {
            List<E> queries = service.getAll();
            return new ResponseEntity<>(mapper.toDTOs(queries), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/execute-single-query-by-id/{id}")
    ResponseEntity<String> executeQuery(@PathVariable(name = "id") long id) {
        try {
            service.executeQuery(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QueryException ex) {
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
