package DatabaseManager.utils;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.DTO.TableQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.TableDoesNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericEntityDTOMapper<E, D> {

    private final EntityDTOMapper mapper;

    public E toEntity(D dto) throws MyException {
        if (dto instanceof CommonQueryEntity) {
            return (E) mapper.toEntity((CommonQueryDTO) dto);
        }
        return (E) mapper.toEntity((TableQueryDTO) dto);
    }

    public D toDTO(E entity) {
        if (entity instanceof CommonQueryDTO) {
            return (D) mapper.toDTO((CommonQueryEntity) entity);
        }
        return (D) mapper.toDTO((TableQueryEntity) entity);
    }

    public List<D> toDTOs(List<E> entities) {
        if (entities.get(0) instanceof CommonQueryEntity) {
            return (List<D>) mapper.toDTOs((List<CommonQueryEntity>) entities, new CommonQueryEntity());
        }
        return (List<D>) mapper.toDTOs((List<TableQueryEntity>) entities, new TableQueryEntity());
    }
}
