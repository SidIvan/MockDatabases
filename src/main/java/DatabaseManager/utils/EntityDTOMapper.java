package DatabaseManager.utils;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOMapper {

    public static CommonQueryEntity toEntity(CommonQueryDTO dto) {
        CommonQueryEntity entity = new CommonQueryEntity();
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        return entity;
    }

    public static CommonQueryDTO toDTO(CommonQueryEntity entity) {
        CommonQueryDTO dto = new CommonQueryDTO();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        return dto;
    }

    public static List<CommonQueryDTO> toDTOs(List<CommonQueryEntity> entities) {
        List<CommonQueryDTO> dtos = new ArrayList<>();
        for (CommonQueryEntity entity : entities) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }
}
