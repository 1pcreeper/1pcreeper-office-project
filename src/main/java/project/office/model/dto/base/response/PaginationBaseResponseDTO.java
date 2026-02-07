package project.office.model.dto.base.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationBaseResponseDTO<DTO> {
    private List<DTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}