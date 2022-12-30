package DatabaseManager.utils;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.DTO.TableQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.entities.TableQueryEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOMapper {

    public static CommonQueryEntity toEntity(CommonQueryDTO dto) {
        CommonQueryEntity entity = new CommonQueryEntity();
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        return entity;
    }

    public static TableQueryEntity toEntity(TableQueryDTO dto) {
        TableQueryEntity entity = new TableQueryEntity();
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        entity.setTableName(dto.getTableName());
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
