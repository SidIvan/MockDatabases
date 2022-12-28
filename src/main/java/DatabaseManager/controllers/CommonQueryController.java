package DatabaseManager.controllers;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.exceptions.QueryInitializationException;
import DatabaseManager.services.CommonQueryService;
import DatabaseManager.utils.EntityDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api/single-query")
public class CommonQueryController {

    @Autowired
    CommonQueryService commonQueryService;

    @PostMapping(value = "/add-new-query",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Long> createQuery(@RequestBody CommonQueryDTO queryDTO) {
        try {
            Long id = commonQueryService.createQuery(EntityDTOMapper.toEntity(queryDTO));
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

    @PutMapping(value = "/modify-single-query",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> changeQuery(@RequestBody CommonQueryDTO queryDTO) {
        try {
            commonQueryService.changeQuery(EntityDTOMapper.toEntity(queryDTO));
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

    @DeleteMapping("/delete-single-query-by-id/{id}")
    ResponseEntity<String> deleteQuery(@PathVariable(name = "id") String id) {
        try {
            commonQueryService.deleteQuery(id);
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

    @GetMapping(value = "/get-single-query-by-id/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommonQueryDTO> getById(@PathVariable(name = "id") long id) {
        try {
            CommonQueryEntity query = commonQueryService.getById(id);
            return new ResponseEntity<>(EntityDTOMapper.toDTO(query), HttpStatus.OK);
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

    @GetMapping(value = "/get-all-single-queries",
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CommonQueryDTO>> getById() {
        try {
            List<CommonQueryEntity> queries = commonQueryService.getAll();
            return new ResponseEntity<>(EntityDTOMapper.toDTOs(queries), HttpStatus.OK);
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
