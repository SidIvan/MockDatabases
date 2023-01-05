package DatabaseManager.utils;

import DatabaseManager.DTO.CommonQueryDTO;
import DatabaseManager.DTO.TableDTO;
import DatabaseManager.DTO.TableQueryDTO;
import DatabaseManager.entities.CommonQueryEntity;
import DatabaseManager.entities.TableEntity;
import DatabaseManager.entities.TableQueryEntity;
import DatabaseManager.exceptions.MyException;
import DatabaseManager.exceptions.QueryException;
import DatabaseManager.repositories.TableEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EntityDTOMapper {

    private final TableEntityRepository tablesRepository;

    public TableDTO toDTO(TableEntity entity) {
        TableDTO dto = new TableDTO();
        dto.setId(entity.getId());
        dto.setTableName(entity.getTableName());
        return dto;
    }

    public CommonQueryEntity toEntity(CommonQueryDTO dto) {
        CommonQueryEntity entity = new CommonQueryEntity();
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        return entity;
    }

    public TableQueryEntity toEntity(TableQueryDTO dto) throws MyException {
        if (dto.getValue() == null) {
            throw new QueryException(20);
        }
        if (dto.getTableName() == null) {
            throw new QueryException(30);
        }
        TableQueryEntity entity = new TableQueryEntity();
        entity.setValue(dto.getValue());
        entity.setTableName(dto.getTableName());
        entity.setId(dto.getId());
        return entity;
    }

    public CommonQueryDTO toDTO(CommonQueryEntity entity) {
        CommonQueryDTO dto = new CommonQueryDTO();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        return dto;
    }

    public TableQueryDTO toDTO(TableQueryEntity entity) {
        TableQueryDTO dto = new TableQueryDTO();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setTableName(entity.getTableName());
        dto.setTableEntity(toDTO(entity.getTableEntity()));
        return dto;
    }

    public List<CommonQueryDTO> toDTOs(List<CommonQueryEntity> entities, CommonQueryEntity type) {
        List<CommonQueryDTO> dtos = new ArrayList<>();
        for (CommonQueryEntity entity : entities) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }

    public List<TableQueryDTO> toDTOs(List<TableQueryEntity> entities, TableQueryEntity type) {
        List<TableQueryDTO> dtos = new ArrayList<>();
        for (TableQueryEntity entity : entities) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }

}
