package DatabaseManager.utils;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericEntityDTOMapper<E, D> {



    public E toEntity(D dto) {
        if (dto instanceof CommonQueryEntity) {
            return (E) EntityDTOMapper.toEntity((CommonQueryDTO) dto);
        }
        return (E) EntityDTOMapper.toEntity((CommonQueryDTO) dto);
    }

    public D toDTO(E entity) {
        if (entity instanceof CommonQueryDTO) {
            return (D) EntityDTOMapper.toDTO((CommonQueryEntity) entity);
        }
        return (D) EntityDTOMapper.toDTO((CommonQueryEntity) entity);
    }

    public List<D> toDTOs(List<E> entities) {
        if (entities.get(0) instanceof CommonQueryEntity) {
            return (List<D>) EntityDTOMapper.toDTOs((List<CommonQueryEntity>) entities);
        }
        return (List<D>) EntityDTOMapper.toDTOs((List<CommonQueryEntity>) entities);
    }
}
