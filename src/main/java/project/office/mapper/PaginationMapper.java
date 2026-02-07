package project.office.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.office.model.dto.base.response.PaginationBaseResponseDTO;

import java.util.List;
import java.util.function.Function;

@Component
public class PaginationMapper {
    public <T, DTO> PaginationBaseResponseDTO<DTO> toDTO(Page<T> pagedEntities,
                                                         Function<T, DTO> mapper) {
        if(pagedEntities==null){
            return null;
        }
        List<DTO> responseDTOs = pagedEntities.stream()
                .map(mapper)
                .toList();
        return PaginationBaseResponseDTO.<DTO>builder()
                .content(responseDTOs)
                .pageNumber(pagedEntities.getNumber())
                .pageSize(pagedEntities.getSize())
                .totalPages(pagedEntities.getTotalPages())
                .totalElements(pagedEntities.getTotalElements())
                .build();
    }
}