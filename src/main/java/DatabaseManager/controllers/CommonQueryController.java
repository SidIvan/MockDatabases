package DatabaseManager.controllers;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.repositories.CommonQueryRepository;
import DatabaseManager.services.CommonQueryService;
import DatabaseManager.utils.GenericEntityDTOMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/single-query")
public class CommonQueryController extends AbstractQueryController<CommonQueryEntity,
        CommonQueryRepository, CommonQueryService, CommonQueryDTO> {

    public CommonQueryController(CommonQueryService service, GenericEntityDTOMapper<CommonQueryEntity, CommonQueryDTO> mapper) {
        super(service, mapper);
    }


}
