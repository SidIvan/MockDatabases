package DatabaseManager.controllers;

import DatabaseManager.DTO.TableQueryDTO;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.repositories.TableQueryRepository;
import DatabaseManager.services.TableQueryService;
import DatabaseManager.utils.GenericEntityDTOMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/table-query")
public class TableQueryController extends AbstractQueryController<TableQueryEntity, TableQueryRepository,
                                                            TableQueryService, TableQueryDTO> {

    public TableQueryController(TableQueryService service, GenericEntityDTOMapper<TableQueryEntity, TableQueryDTO> mapper) {
        super(service, mapper);
    }

}
